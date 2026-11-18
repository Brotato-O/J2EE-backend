package J2EE.com.example.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;

import J2EE.com.example.project.dto.response.ShipmentResponse;
import J2EE.com.example.project.service.FulfillmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/fulfillments")
@RequiredArgsConstructor
public class FulfillmentController {

    private final FulfillmentService fulfillmentService;

    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên', 'Nhân Viên Kho')")
    @PostMapping("/orders/{orderId}/shipment")
    public ResponseEntity<ShipmentResponse> createShipment(@PathVariable Integer orderId) {
        return ResponseEntity.ok(fulfillmentService.createShipment(orderId));
    }

    @GetMapping("/orders/{orderId}/shipment")
    public ResponseEntity<ShipmentResponse> getShipment(@PathVariable Integer orderId) {
        return ResponseEntity.ok(fulfillmentService.getShipment(orderId));
    }

    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên', 'Nhân Viên Kho')")
    @DeleteMapping("/orders/{orderId}/shipment")
    public ResponseEntity<Void> cancelShipment(@PathVariable Integer orderId) {
        fulfillmentService.cancelShipment(orderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/webhooks/ghn")
    public ResponseEntity<Void> ghnWebhook(
            @RequestBody JsonNode body,
            @RequestHeader(value = "X-GHN-Webhook-Secret", required = false) String webhookSecret) {
        String orderCode = body.path("OrderCode").asText("");
        String eventType = body.path("Type").asText("");
        String status = body.path("Status").asText("");
        String eventTime = body.path("Time").asText("");
        if (orderCode.isBlank() || eventType.isBlank() || eventTime.isBlank()) {
            throw new J2EE.com.example.project.exception.AppException(
                    J2EE.com.example.project.exception.ErrorCode.INVALID_GHN_WEBHOOK);
        }

        log.info("Received GHN webhook: orderCode={}, eventType={}, status={}, eventTime={}",
                orderCode, eventType, status, eventTime);

        fulfillmentService.handleGhnWebhook(
                orderCode,
                eventType,
                status,
                eventTime,
                body.path("Reason").asText(""),
                body.toString(),
                webhookSecret);
        return ResponseEntity.ok().build();
    }

}
