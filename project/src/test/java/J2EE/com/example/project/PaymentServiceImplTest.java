package J2EE.com.example.project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.Duration;
import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import J2EE.com.example.project.configuration.VietQrProperties;
import J2EE.com.example.project.dto.request.PaymentNotificationRequest;
import J2EE.com.example.project.entity.Order;
import J2EE.com.example.project.entity.Payment;
import J2EE.com.example.project.enums.PaymentStatus;
import J2EE.com.example.project.integration.MockVietQrProvider;
import J2EE.com.example.project.repository.OrderRepository;
import J2EE.com.example.project.repository.PaymentRepository;
import J2EE.com.example.project.repository.PaymentTransactionRepository;
import J2EE.com.example.project.repository.PaymentWebhookEventRepository;
import J2EE.com.example.project.service.OrderLifecycleService;
import J2EE.com.example.project.service.impl.PaymentServiceImpl;

class PaymentServiceImplTest {

    @Test
    void rejectsNotificationWithWrongAmount() {
        OrderRepository orders = mock(OrderRepository.class);
        PaymentRepository payments = mock(PaymentRepository.class);
        PaymentTransactionRepository transactions = mock(PaymentTransactionRepository.class);
        PaymentWebhookEventRepository events = mock(PaymentWebhookEventRepository.class);
        OrderLifecycleService lifecycle = mock(OrderLifecycleService.class);
        VietQrProperties properties = new VietQrProperties();
        properties.setExpiration(Duration.ofMinutes(15));
        PaymentServiceImpl service = new PaymentServiceImpl(
                orders, payments, transactions, events, lifecycle,
                new MockVietQrProvider(), List.of(), properties, new ObjectMapper());

        Payment payment = Payment.builder()
                .order(Order.builder().id(1).build())
                .transactionReference("REF-1")
                .amount(BigDecimal.TEN)
                .receivingAccount("123")
                .status(PaymentStatus.PENDING)
                .expiresAt(Instant.now().plusSeconds(600))
                .build();
        when(orders.findByIdForUpdate(1)).thenReturn(Optional.of(payment.getOrder()));
        when(payments.findByTransactionReferenceForUpdate("REF-1")).thenReturn(Optional.of(payment));

        PaymentNotificationRequest request = new PaymentNotificationRequest();
        request.setNotificationId("N1");
        request.setTransactionId("T1");
        request.setTransactionReference("REF-1");
        request.setAmount(BigDecimal.ONE);
        request.setReceivingAccount("123");

        assertThrows(IllegalArgumentException.class, () -> service.reconcile(request));
        verify(transactions, never()).save(any());
    }
}
