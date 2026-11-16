package J2EE.com.example.project.integration;

import java.util.UUID;

import org.springframework.stereotype.Component;

import J2EE.com.example.project.entity.Order;
import J2EE.com.example.project.entity.Shipment;
import J2EE.com.example.project.enums.ShippingProviderType;

@Component
public class MockShippingProvider implements ShippingProvider {
    @Override
    public ShippingProviderType type() {
        return ShippingProviderType.GHTK;
    }
    @Override
    public ShippingCreateResult createShipment(Order order, Shipment shipment) {
        String code = "MOCK-" + order.getId() + "-" + UUID.randomUUID().toString().substring(0, 8);
        return new ShippingCreateResult(code, code);
    }

    @Override
    public ShippingStatusResult getStatus(String trackingCode) {
        return new ShippingStatusResult(1, "Mock shipment is awaiting confirmation");
    }

    @Override
    public void cancel(String trackingCode) {
    }
}
