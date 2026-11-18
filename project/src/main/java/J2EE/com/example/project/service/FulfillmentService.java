package J2EE.com.example.project.service;

import J2EE.com.example.project.dto.response.ShipmentResponse;

public interface FulfillmentService {
    ShipmentResponse createShipment(Integer orderId);
    ShipmentResponse getShipment(Integer orderId);
    void cancelShipment(Integer orderId);
    void handleGhnWebhook(String orderCode, String eventType, String status,
            String eventTime, String reason, String payload, String webhookSecret);
}
