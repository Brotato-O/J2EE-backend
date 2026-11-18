package J2EE.com.example.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import J2EE.com.example.project.configuration.GhnProperties;
import J2EE.com.example.project.integration.ShippingProvider;
import J2EE.com.example.project.repository.FulfillmentRepository;
import J2EE.com.example.project.repository.OrderRepository;
import J2EE.com.example.project.repository.PaymentRepository;
import J2EE.com.example.project.repository.OrderDetailRepository;
import J2EE.com.example.project.repository.ProChitietRepository;
import J2EE.com.example.project.repository.ShipmentRepository;
import J2EE.com.example.project.repository.ShippingWebhookEventRepository;
import J2EE.com.example.project.service.OrderLifecycleService;
import J2EE.com.example.project.service.impl.FulfillmentServiceImpl;
import J2EE.com.example.project.entity.Fulfillment;
import J2EE.com.example.project.entity.Order;
import J2EE.com.example.project.entity.Shipment;
import J2EE.com.example.project.entity.OrderDetail;
import J2EE.com.example.project.entity.Product;
import J2EE.com.example.project.entity.Color;
import J2EE.com.example.project.entity.Size;
import J2EE.com.example.project.entity.ProChitiet;
import J2EE.com.example.project.enums.ShipmentStatus;
import J2EE.com.example.project.enums.FulfillmentStatus;

class FulfillmentServiceImplTest {

    @Test
    void rejectsWebhookWhenConfiguredHashDoesNotMatch() {
        GhnProperties properties = new GhnProperties();
        properties.setWebhookSecret("expected");
        FulfillmentServiceImpl service = new FulfillmentServiceImpl(
                mock(OrderRepository.class),
                mock(FulfillmentRepository.class),
                mock(ShipmentRepository.class),
                mock(ShippingWebhookEventRepository.class),
                mock(OrderLifecycleService.class),
                mock(ShippingProvider.class),
                properties,
                new ObjectMapper(),
                mock(PaymentRepository.class),
                mock(OrderDetailRepository.class),
                mock(ProChitietRepository.class));

        assertThrows(RuntimeException.class, () -> service.handleGhnWebhook(
                "label", "switch_status", "delivered", "2026-11-15T12:00:00+07:00",
                "", "{}", "wrong"));
    }

    @Test
    void ignoresDuplicateWebhookBeforeCallingShipmentRepository() {
        GhnProperties properties = new GhnProperties();
        properties.setWebhookSecret("expected");
        ShippingWebhookEventRepository events = mock(ShippingWebhookEventRepository.class);
        ShipmentRepository shipments = mock(ShipmentRepository.class);
        when(events.findByProviderAndEventKey(anyString(), anyString())).thenReturn(
                Optional.of(new J2EE.com.example.project.entity.ShippingWebhookEvent()));
        FulfillmentServiceImpl service = new FulfillmentServiceImpl(
                mock(OrderRepository.class),
                mock(FulfillmentRepository.class),
                shipments,
                events,
                mock(OrderLifecycleService.class),
                mock(ShippingProvider.class),
                properties,
                new ObjectMapper(),
                mock(PaymentRepository.class),
                mock(OrderDetailRepository.class),
                mock(ProChitietRepository.class));

        service.handleGhnWebhook("label", "switch_status", "delivered",
                "2026-11-15T12:00:00+07:00", "", "{}", "expected");

        verifyNoInteractions(shipments);
    }

    @Test
    void cancelShipmentRestocksVariantAndRecordsCancelledStatus() {
        GhnProperties properties = new GhnProperties();
        FulfillmentRepository fulfillments = mock(FulfillmentRepository.class);
        ShipmentRepository shipments = mock(ShipmentRepository.class);
        ShippingProvider provider = mock(ShippingProvider.class);
        OrderDetailRepository orderDetails = mock(OrderDetailRepository.class);
        ProChitietRepository variants = mock(ProChitietRepository.class);
        OrderLifecycleService lifecycle = mock(OrderLifecycleService.class);

        Order order = Order.builder().id(7).status("PACKING").build();
        Fulfillment fulfillment = Fulfillment.builder()
                .id(3L).order(order).status(FulfillmentStatus.PACKING).build();
        Shipment shipment = Shipment.builder()
                .fulfillment(fulfillment)
                .trackingCode("GHN-123")
                .status(ShipmentStatus.CONFIRMED)
                .build();
        when(fulfillments.findByOrderIdForUpdate(7)).thenReturn(Optional.of(fulfillment));
        when(shipments.findByFulfillmentIdForUpdate(3L)).thenReturn(Optional.of(shipment));

        Product product = Product.builder().id(1).build();
        Color color = Color.builder().id(2).build();
        Size size = Size.builder().id(3).build();
        OrderDetail detail = OrderDetail.builder()
                .product(product).color(color).size(size).quantity(2).build();
        when(orderDetails.findByOrderId(7)).thenReturn(java.util.List.of(detail));
        ProChitiet variant = ProChitiet.builder().stockQuantity(5).build();
        when(variants.findByProductIdAndColorIdAndSizeId(1, 2, 3))
                .thenReturn(Optional.of(variant));

        FulfillmentServiceImpl service = new FulfillmentServiceImpl(
                mock(OrderRepository.class),
                fulfillments,
                shipments,
                mock(ShippingWebhookEventRepository.class),
                lifecycle,
                provider,
                properties,
                new ObjectMapper(),
                mock(PaymentRepository.class),
                orderDetails,
                variants);

        service.cancelShipment(7);

        verify(provider).cancel("GHN-123");
        assertEquals(7, variant.getStockQuantity());
        verify(shipments).save(shipment);
        verify(lifecycle).transition(
                7, FulfillmentStatus.CANCELLED, "Shipment cancelled");
    }
}
