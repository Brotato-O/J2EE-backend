package J2EE.com.example.project.service.impl;

import java.time.Instant;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import J2EE.com.example.project.configuration.GhtkProperties;
import J2EE.com.example.project.dto.response.ShipmentResponse;
import J2EE.com.example.project.entity.Fulfillment;
import J2EE.com.example.project.enums.FulfillmentStatus;
import J2EE.com.example.project.entity.Order;
import J2EE.com.example.project.entity.Shipment;
import J2EE.com.example.project.enums.ShipmentStatus;
import J2EE.com.example.project.enums.ShippingProviderType;
import J2EE.com.example.project.entity.ShippingWebhookEvent;
import J2EE.com.example.project.enums.PaymentStatus;
import J2EE.com.example.project.enums.PaymentMethod;
import J2EE.com.example.project.integration.ShippingCreateResult;
import J2EE.com.example.project.integration.ShippingProvider;
import J2EE.com.example.project.repository.FulfillmentRepository;
import J2EE.com.example.project.repository.OrderRepository;
import J2EE.com.example.project.repository.ShipmentRepository;
import J2EE.com.example.project.repository.ShippingWebhookEventRepository;
import J2EE.com.example.project.repository.PaymentRepository;
import J2EE.com.example.project.service.FulfillmentService;
import J2EE.com.example.project.service.OrderLifecycleService;
import J2EE.com.example.project.exception.AppException;
import J2EE.com.example.project.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FulfillmentServiceImpl implements FulfillmentService {

    private final OrderRepository orderRepository;
    private final FulfillmentRepository fulfillmentRepository;
    private final ShipmentRepository shipmentRepository;
    private final ShippingWebhookEventRepository webhookEventRepository;
    private final OrderLifecycleService lifecycleService;
    private final ShippingProvider ghtkProvider;
    private final GhtkProperties ghtkProperties;
    private final ObjectMapper objectMapper;
    private final PaymentRepository paymentRepository;

    @Override
    @Transactional
    public ShipmentResponse createShipment(Integer orderId) {
        Order order = orderRepository.findByIdForUpdate(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        if (order.getPaymentMethod() != PaymentMethod.COD) {
            if (paymentRepository.findFirstByOrderIdAndStatusOrderByCreatedAtDesc(orderId, PaymentStatus.SUCCESS)
                    .isEmpty()) {
                throw new AppException(ErrorCode.PAYMENT_NOT_PENDING);
            }
        }
        Fulfillment fulfillment = fulfillmentRepository.findByOrderIdForUpdate(orderId)
                .orElseGet(() -> fulfillmentRepository.save(Fulfillment.builder()
                        .order(order)
                        .status(FulfillmentStatus.CONFIRMED)
                        .build()));
        Shipment existing = shipmentRepository.findByFulfillmentId(fulfillment.getId()).orElse(null);
        if (existing != null && existing.getTrackingCode() != null) {
            return toResponse(existing);
        }
        if (!lifecycleService.tryTransition(order, FulfillmentStatus.PACKING, "Shipment creation requested")) {
            throw new AppException(ErrorCode.INVALID_ORDER_TRANSITION);
        }

        Shipment shipment = existing == null
                ? Shipment.builder().fulfillment(fulfillment).provider(ShippingProviderType.GHTK).build()
                : existing;
        shipment.setStatus(ShipmentStatus.CREATED);
        shipment.setProvider(ShippingProviderType.GHTK);
        ShippingCreateResult result = ghtkProvider.createShipment(order, shipment);
        shipment.setTrackingCode(result.trackingCode());
        shipment.setProviderOrderCode(result.providerOrderCode());
        shipment.setStatus(ShipmentStatus.CONFIRMED);
        shipmentRepository.save(shipment);
        lifecycleService.tryTransition(order, FulfillmentStatus.SHIPPING,
                "GHTK shipment created");
        return toResponse(shipment);
    }

    @Override
    @Transactional(readOnly = true)
    public ShipmentResponse getShipment(Integer orderId) {
        Shipment shipment = shipmentRepository.findByFulfillmentId(
                fulfillmentRepository.findByOrderId(orderId)
                        .orElseThrow(() -> new AppException(ErrorCode.FULFILLMENT_NOT_FOUND))
                        .getId())
                .orElseThrow(() -> new AppException(ErrorCode.SHIPMENT_NOT_FOUND));
        return toResponse(shipment);
    }

    @Override
    @Transactional
    public void cancelShipment(Integer orderId) {
        Shipment shipment = getShipmentEntity(orderId);
        if (shipment.getStatus() == ShipmentStatus.DELIVERED
                || shipment.getStatus() == ShipmentStatus.RETURNED) {
            throw new AppException(ErrorCode.SHIPMENT_CANNOT_CANCEL);
        }
        ghtkProvider.cancel(shipment.getTrackingCode());
        shipment.setStatus(ShipmentStatus.CANCELLED);
        shipmentRepository.save(shipment);
        lifecycleService.transition(orderId, FulfillmentStatus.CANCELLED, "Shipment cancelled");
    }

    @Override
    @Transactional
    public void handleGhtkWebhook(String hash, String labelId, String partnerId, Integer statusId,
            String actionTime, String reasonCode, String reason, String payload) {
        if (ghtkProperties.getWebhookHash() == null
                || ghtkProperties.getWebhookHash().isBlank()
                || !ghtkProperties.getWebhookHash().equals(hash)) {
            throw new IllegalArgumentException(ErrorCode.INVALID_GHTK_WEBHOOK.getMessage());
        }
        String eventKey = labelId + ":" + statusId + ":" + actionTime;
        if (webhookEventRepository.findByProviderAndEventKey("GHTK", eventKey).isPresent()) {
            return;
        }
        ShippingWebhookEvent event = ShippingWebhookEvent.builder()
                .provider("GHTK")
                .eventKey(eventKey)
                .trackingCode(labelId)
                .statusId(statusId)
                .payload(payload)
                .build();
        try {
            webhookEventRepository.saveAndFlush(event);
        } catch (DataIntegrityViolationException duplicate) {
            return;
        }

        Shipment shipment = shipmentRepository.findByTrackingCodeForUpdate(labelId)
                .orElseThrow(() -> new AppException(ErrorCode.SHIPMENT_NOT_FOUND));
        ShipmentStatus shipmentStatus = mapShipmentStatus(statusId);
        if (statusRank(shipmentStatus) < statusRank(shipment.getStatus())
                && shipmentStatus != ShipmentStatus.DELIVERY_FAILED) {
            event.setProcessed(true);
            event.setProcessedAt(Instant.now());
            return;
        }
        shipment.setStatus(shipmentStatus);
        shipmentRepository.save(shipment);
        FulfillmentStatus fulfillmentStatus = mapFulfillmentStatus(statusId);
        lifecycleService.tryTransition(shipment.getFulfillment().getOrder(), fulfillmentStatus,
                "GHTK webhook " + statusId + ": " + reason);
        event.setProcessed(true);
        event.setProcessedAt(Instant.now());
    }

    private Shipment getShipmentEntity(Integer orderId) {
        Fulfillment fulfillment = fulfillmentRepository.findByOrderIdForUpdate(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.FULFILLMENT_NOT_FOUND));
        return shipmentRepository.findByFulfillmentId(fulfillment.getId())
                .orElseThrow(() -> new AppException(ErrorCode.SHIPMENT_NOT_FOUND));
    }

    private ShipmentStatus mapShipmentStatus(Integer statusId) {
        return switch (statusId) {
            case -1 -> ShipmentStatus.CANCELLED;
            case 2, 12, 30, 31, 91 -> ShipmentStatus.CONFIRMED;
            case 3, 123 -> ShipmentStatus.PICKED_UP;
            case 4, 10, 410 -> ShipmentStatus.SHIPPING;
            case 5, 6, 45 -> ShipmentStatus.DELIVERED;
            case 7, 8, 127, 128, 49 -> ShipmentStatus.DELIVERY_FAILED;
            case 9 -> ShipmentStatus.DELIVERY_FAILED;
            case 20 -> ShipmentStatus.RETURNING;
            case 21, 26 -> ShipmentStatus.RETURNED;
            default -> ShipmentStatus.CREATED;
        };
    }

    private FulfillmentStatus mapFulfillmentStatus(Integer statusId) {
        return switch (statusId) {
            case -1 -> FulfillmentStatus.CANCELLED;
            case 5, 6, 45 -> FulfillmentStatus.DELIVERED;
            case 9, 49, 410 -> FulfillmentStatus.DELIVERY_FAILED;
            case 20, 21, 26 -> FulfillmentStatus.RETURNED;
            default -> FulfillmentStatus.SHIPPING;
        };
    }

    private int statusRank(ShipmentStatus status) {
        return switch (status) {
            case CREATED -> 0;
            case CONFIRMED -> 1;
            case PICKED_UP -> 2;
            case SHIPPING -> 3;
            case DELIVERY_FAILED -> 4;
            case DELIVERED -> 5;
            case RETURNING -> 6;
            case RETURNED -> 7;
            case CANCELLED -> 8;
        };
    }

    private ShipmentResponse toResponse(Shipment shipment) {
        return ShipmentResponse.builder()
                .orderId(shipment.getFulfillment().getOrder().getId())
                .fulfillmentId(shipment.getFulfillment().getId())
                .provider(shipment.getProvider().name())
                .trackingCode(shipment.getTrackingCode())
                .providerOrderCode(shipment.getProviderOrderCode())
                .fulfillmentStatus(shipment.getFulfillment().getStatus())
                .shipmentStatus(shipment.getStatus())
                .build();
    }
}
