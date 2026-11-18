package J2EE.com.example.project.service.impl;

import java.time.Instant;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import J2EE.com.example.project.configuration.GhnProperties;
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
import J2EE.com.example.project.repository.OrderDetailRepository;
import J2EE.com.example.project.repository.ProChitietRepository;
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
    private final ShippingProvider ghnProvider;
    private final GhnProperties ghnProperties;
    private final ObjectMapper objectMapper;
    private final PaymentRepository paymentRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProChitietRepository proChitietRepository;

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
                        .status(FulfillmentStatus.fromValue(order.getStatus()))
                        .build()));
        Shipment existing = shipmentRepository.findByFulfillmentId(fulfillment.getId()).orElse(null);
        if (existing != null && existing.getTrackingCode() != null) {
            return toResponse(existing);
        }
        FulfillmentStatus currentStatus = FulfillmentStatus.fromValue(order.getStatus());
        if (currentStatus != FulfillmentStatus.CONFIRMED) {
            throw new AppException(ErrorCode.INVALID_ORDER_TRANSITION);
        }
        if (!lifecycleService.tryTransition(order, FulfillmentStatus.PACKING, "Shipment creation requested")) {
            throw new AppException(ErrorCode.INVALID_ORDER_TRANSITION);
        }

        Shipment shipment = existing == null
                ? Shipment.builder().fulfillment(fulfillment).provider(ShippingProviderType.GHN).build()
                : existing;
        shipment.setStatus(ShipmentStatus.CREATED);
        shipment.setProvider(ShippingProviderType.GHN);
        ShippingCreateResult result = ghnProvider.createShipment(order, shipment);
        shipment.setTrackingCode(result.trackingCode());
        shipment.setProviderOrderCode(result.providerOrderCode());
        shipment.setStatus(ShipmentStatus.CONFIRMED);
        shipmentRepository.save(shipment);
        lifecycleService.tryTransition(order, FulfillmentStatus.SHIPPING,
                "GHN shipment created");
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
        if (shipment.getStatus() == ShipmentStatus.CANCELLED) {
            return;
        }
        if (shipment.getStatus() == ShipmentStatus.DELIVERED
                || shipment.getStatus() == ShipmentStatus.RETURNED
                || shipment.getStatus() == ShipmentStatus.RETURNING
                || shipment.getStatus() == ShipmentStatus.PICKED_UP
                || shipment.getStatus() == ShipmentStatus.SHIPPING
                || shipment.getStatus() == ShipmentStatus.DELIVERY_FAILED) {
            throw new AppException(ErrorCode.SHIPMENT_CANNOT_CANCEL);
        }
        FulfillmentStatus orderStatus = FulfillmentStatus.fromValue(
                shipment.getFulfillment().getOrder().getStatus());
        if (orderStatus != FulfillmentStatus.NEW
                && orderStatus != FulfillmentStatus.CONFIRMED
                && orderStatus != FulfillmentStatus.PACKING) {
            throw new AppException(ErrorCode.SHIPMENT_CANNOT_CANCEL);
        }
        if (shipment.getTrackingCode() == null || shipment.getTrackingCode().isBlank()) {
            throw new AppException(ErrorCode.SHIPPING_RESPONSE_INVALID);
        }
        ghnProvider.cancel(shipment.getTrackingCode());
        restoreStock(shipment.getFulfillment().getOrder());
        shipment.setStatus(ShipmentStatus.CANCELLED);
        shipment.setStockReleased(true);
        shipmentRepository.save(shipment);
        lifecycleService.transition(orderId, FulfillmentStatus.CANCELLED, "Shipment cancelled");
    }

    @Override
    @Transactional
    public void handleGhnWebhook(String orderCode, String eventType, String status,
            String eventTime, String reason, String payload, String webhookSecret) {
        if (ghnProperties.getWebhookSecret() != null
                && !ghnProperties.getWebhookSecret().isBlank()
                && !ghnProperties.getWebhookSecret().equals(webhookSecret)) {
            throw new AppException(ErrorCode.INVALID_GHN_WEBHOOK);
        }


        String eventKey = orderCode + ":" + eventType + ":" + eventTime;
        if (webhookEventRepository.findByProviderAndEventKey("GHN", eventKey).isPresent()) {
            return;
        }
        ShippingWebhookEvent event = ShippingWebhookEvent.builder()
                .provider("GHN")
                .eventKey(eventKey)
                .trackingCode(orderCode)
                .statusId(0)
                .statusCode(status)
                .payload(payload)
                .build();
        try {
            webhookEventRepository.saveAndFlush(event);
        } catch (DataIntegrityViolationException duplicate) {
            return;
        }

        Shipment shipment = shipmentRepository.findByTrackingCodeForUpdate(orderCode)
                .orElseThrow(() -> new AppException(ErrorCode.SHIPMENT_NOT_FOUND));
        ShipmentStatus shipmentStatus = mapShipmentStatus(status);
        if (statusRank(shipmentStatus) < statusRank(shipment.getStatus())
                && shipmentStatus != ShipmentStatus.DELIVERY_FAILED) {
            event.setProcessed(true);
            event.setProcessedAt(Instant.now());
            return;
        }
        shipment.setStatus(shipmentStatus);
        shipmentRepository.save(shipment);
        FulfillmentStatus fulfillmentStatus = mapFulfillmentStatus(status);
        lifecycleService.tryTransition(shipment.getFulfillment().getOrder(), fulfillmentStatus,
                "GHN webhook " + status + ": " + reason);
        event.setProcessed(true);
        event.setProcessedAt(Instant.now());
    }

    private Shipment getShipmentEntity(Integer orderId) {
        Fulfillment fulfillment = fulfillmentRepository.findByOrderIdForUpdate(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.FULFILLMENT_NOT_FOUND));
        return shipmentRepository.findByFulfillmentIdForUpdate(fulfillment.getId())
                .orElseThrow(() -> new AppException(ErrorCode.SHIPMENT_NOT_FOUND));
    }

    private void restoreStock(Order order) {
        orderDetailRepository.findByOrderId(order.getId()).forEach(detail -> {
            proChitietRepository
                    .findByProductIdAndColorIdAndSizeId(
                            detail.getProduct().getId(),
                            detail.getColor().getId(),
                            detail.getSize().getId())
                    .ifPresent(variant -> {
                        int restoredStock = (variant.getStockQuantity() == null ? 0 : variant.getStockQuantity())
                                + detail.getQuantity();
                        variant.setStockQuantity(restoredStock);
                        proChitietRepository.save(variant);
                    });
        });
    }

    private ShipmentStatus mapShipmentStatus(String status) {
        return switch (status == null ? "" : status.toLowerCase()) {
            case "cancel" -> ShipmentStatus.CANCELLED;
            case "ready_to_pick", "picking" -> ShipmentStatus.CONFIRMED;
            case "picked" -> ShipmentStatus.PICKED_UP;
            case "delivering", "delivery_fail" -> status.equalsIgnoreCase("delivery_fail")
                    ? ShipmentStatus.DELIVERY_FAILED : ShipmentStatus.SHIPPING;
            case "delivered" -> ShipmentStatus.DELIVERED;
            case "return", "returning" -> ShipmentStatus.RETURNING;
            case "returned", "return_fail", "damage", "lost" -> status.equalsIgnoreCase("returned")
                    ? ShipmentStatus.RETURNED : ShipmentStatus.DELIVERY_FAILED;
            default -> ShipmentStatus.CREATED;
        };
    }

    private FulfillmentStatus mapFulfillmentStatus(String status) {
        return switch (status == null ? "" : status.toLowerCase()) {
            case "cancel" -> FulfillmentStatus.CANCELLED;
            case "delivered" -> FulfillmentStatus.DELIVERED;
            case "delivery_fail", "damage", "lost", "return_fail" -> FulfillmentStatus.DELIVERY_FAILED;
            case "return", "returning", "returned" -> FulfillmentStatus.RETURNED;
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
