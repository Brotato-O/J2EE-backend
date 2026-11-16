package J2EE.com.example.project;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import J2EE.com.example.project.configuration.GhtkProperties;
import J2EE.com.example.project.integration.ShippingProvider;
import J2EE.com.example.project.repository.FulfillmentRepository;
import J2EE.com.example.project.repository.OrderRepository;
import J2EE.com.example.project.repository.PaymentRepository;
import J2EE.com.example.project.repository.ShipmentRepository;
import J2EE.com.example.project.repository.ShippingWebhookEventRepository;
import J2EE.com.example.project.service.OrderLifecycleService;
import J2EE.com.example.project.service.impl.FulfillmentServiceImpl;

class FulfillmentServiceImplTest {

    @Test
    void rejectsWebhookWhenConfiguredHashDoesNotMatch() {
        GhtkProperties properties = new GhtkProperties();
        properties.setWebhookHash("expected");
        FulfillmentServiceImpl service = new FulfillmentServiceImpl(
                mock(OrderRepository.class),
                mock(FulfillmentRepository.class),
                mock(ShipmentRepository.class),
                mock(ShippingWebhookEventRepository.class),
                mock(OrderLifecycleService.class),
                mock(ShippingProvider.class),
                properties,
                new ObjectMapper(),
                mock(PaymentRepository.class));

        assertThrows(IllegalArgumentException.class, () -> service.handleGhtkWebhook(
                "wrong", "label", "partner", 5, "2026-11-15T12:00:00+07:00",
                "", "", "{}"));
    }

    @Test
    void ignoresDuplicateWebhookBeforeCallingShipmentRepository() {
        GhtkProperties properties = new GhtkProperties();
        properties.setWebhookHash("expected");
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
                mock(PaymentRepository.class));

        service.handleGhtkWebhook("expected", "label", "partner", 5,
                "2026-11-15T12:00:00+07:00", "", "", "{}");

        verifyNoInteractions(shipments);
    }
}
