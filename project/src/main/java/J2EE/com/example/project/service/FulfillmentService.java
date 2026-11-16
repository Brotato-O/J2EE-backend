package J2EE.com.example.project.service;

import J2EE.com.example.project.dto.response.ShipmentResponse;

public interface FulfillmentService {
    ShipmentResponse createShipment(Integer orderId);
    ShipmentResponse getShipment(Integer orderId);
    void cancelShipment(Integer orderId);
    void handleGhtkWebhook(String hash, String labelId, String partnerId, Integer statusId,
            String actionTime, String reasonCode, String reason, String payload);
}
