package J2EE.com.example.project.integration;

import J2EE.com.example.project.entity.Order;
import J2EE.com.example.project.entity.Shipment;
import J2EE.com.example.project.enums.ShippingProviderType;

public interface ShippingProvider {
    ShippingProviderType type();
    ShippingCreateResult createShipment(Order order, Shipment shipment);
    ShippingStatusResult getStatus(String trackingCode);
    void cancel(String trackingCode);
}
