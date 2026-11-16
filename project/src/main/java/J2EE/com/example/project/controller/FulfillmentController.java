package J2EE.com.example.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import J2EE.com.example.project.dto.response.ShipmentResponse;
import J2EE.com.example.project.service.FulfillmentService;
import lombok.RequiredArgsConstructor;

@RestController
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

    @PostMapping("/webhooks/ghtk")
    public ResponseEntity<Void> ghtkWebhook(
            @RequestParam String hash,
            @RequestParam("label_id") String labelId,
            @RequestParam("partner_id") String partnerId,
            @RequestParam("status_id") Integer statusId,
            @RequestParam("action_time") String actionTime,
            @RequestParam(name = "reason_code", required = false) String reasonCode,
            @RequestParam(name = "reason", required = false) String reason) {
        fulfillmentService.handleGhtkWebhook(hash, labelId, partnerId, statusId,
                actionTime, reasonCode, reason,
                String.format("{\"label_id\":\"%s\",\"partner_id\":\"%s\",\"status_id\":%d,\"action_time\":\"%s\"}",
                        labelId, partnerId, statusId, actionTime));
        return ResponseEntity.ok().build();
    }

}
