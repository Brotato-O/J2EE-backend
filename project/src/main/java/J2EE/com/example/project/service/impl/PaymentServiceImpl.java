package J2EE.com.example.project.service.impl;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.Duration;
import java.util.HexFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import J2EE.com.example.project.configuration.VietQrProperties;
import J2EE.com.example.project.dto.request.PaymentNotificationRequest;
import J2EE.com.example.project.dto.response.PaymentQrResponse;
import J2EE.com.example.project.entity.Order;
import J2EE.com.example.project.entity.Payment;
import J2EE.com.example.project.enums.PaymentStatus;
import J2EE.com.example.project.enums.PaymentProviderType;
import J2EE.com.example.project.entity.PaymentTransaction;
import J2EE.com.example.project.entity.PaymentWebhookEvent;
import J2EE.com.example.project.enums.FulfillmentStatus;
import J2EE.com.example.project.integration.QrData;
import J2EE.com.example.project.integration.VietQrProvider;
import J2EE.com.example.project.integration.OnlinePaymentProvider;
import J2EE.com.example.project.integration.PaymentInitiation;
import J2EE.com.example.project.repository.OrderRepository;
import J2EE.com.example.project.repository.PaymentRepository;
import J2EE.com.example.project.repository.PaymentTransactionRepository;
import J2EE.com.example.project.repository.PaymentWebhookEventRepository;
import J2EE.com.example.project.service.OrderLifecycleService;
import J2EE.com.example.project.service.PaymentService;
import J2EE.com.example.project.exception.AppException;
import J2EE.com.example.project.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentTransactionRepository transactionRepository;
    private final PaymentWebhookEventRepository webhookEventRepository;
    private final OrderLifecycleService lifecycleService;
    private final VietQrProvider vietQrProvider;
    private final java.util.List<OnlinePaymentProvider> onlinePaymentProviders;
    private final VietQrProperties properties;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public PaymentQrResponse createOrGetPayment(Integer orderId) {
        Order order = orderRepository.findByIdForUpdate(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Payment payment = findCurrentPayment(orderId);
        if (payment != null) {
            if (payment.getStatus() == PaymentStatus.PENDING) {
                return toResponse(payment);
            }
            if (payment.getStatus() == PaymentStatus.SUCCESS) {
                return toResponse(payment);
            }
        }

        BigDecimal amount = BigDecimal.valueOf(order.getTotalPrice());
        if (amount.signum() <= 0) {
            throw new AppException(ErrorCode.PAYMENT_AMOUNT_INVALID);
        }
        Payment created = Payment.builder()
                .order(order)
                .transactionReference("ORD-" + orderId + "-"
                        + UUID.randomUUID().toString().replace("-", "").substring(0, 16))
                .amount(amount)
                .receivingAccount(properties.getBankAccount())
                .status(PaymentStatus.PENDING)
                .expiresAt(Instant.now().plus(properties.getExpiration()))
                .build();
        payment = paymentRepository.saveAndFlush(created);
        return toResponse(payment);
    }

    @Override
    @Transactional
    public void reconcile(PaymentNotificationRequest request) {
        validateChecksum(request);
        reconcileInternal(request, PaymentProviderType.VIETQR.name());
    }

    private void reconcileInternal(PaymentNotificationRequest request, String providerName) {
        Payment payment = paymentRepository.findByTransactionReferenceForUpdate(
                request.getTransactionReference())
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));
        payment.setProviderTransactionReference(request.getTransactionReference());
        payment.setProviderTransactionNo(request.getTransactionId());
        Order order = orderRepository.findByIdForUpdate(payment.getOrder().getId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            return;
        }
        if (paymentRepository.findFirstByOrderIdAndStatusOrderByCreatedAtDesc(
                order.getId(), PaymentStatus.SUCCESS).isPresent()) {
            throw new AppException(ErrorCode.PAYMENT_ALREADY_SUCCESSFUL);
        }

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new AppException(ErrorCode.PAYMENT_NOT_PENDING);
        }
        if (payment.getExpiresAt().isBefore(Instant.now())) {
            payment.setStatus(PaymentStatus.EXPIRED);
            paymentRepository.save(payment);
            throw new AppException(ErrorCode.PAYMENT_EXPIRED);
        }
        if (payment.getAmount().compareTo(request.getAmount()) != 0
                || (payment.getReceivingAccount() != null
                        && !Objects.equals(payment.getReceivingAccount(), request.getReceivingAccount()))) {
            throw new IllegalArgumentException(ErrorCode.PAYMENT_DATA_MISMATCH.getMessage());
        }
        if (webhookEventRepository.findByProviderAndNotificationId(providerName,
                request.getNotificationId()).isPresent()) {
            return;
        }
        PaymentWebhookEvent event = PaymentWebhookEvent.builder()
                .provider(providerName)
                .notificationId(request.getNotificationId())
                .payload(writePayload(request))
                .build();
        try {
            webhookEventRepository.saveAndFlush(event);
        } catch (DataIntegrityViolationException duplicate) {
            return;
        }
        PaymentProviderType paymentProvider = PaymentProviderType.valueOf(providerName);
        if (transactionRepository.existsByProviderAndProviderTransactionId(
                paymentProvider, request.getTransactionId())) {
            return;
        }
        PaymentTransaction transaction = PaymentTransaction.builder()
                .payment(payment)
                .provider(paymentProvider)
                .providerTransactionId(request.getTransactionId())
                .amount(request.getAmount())
                .receivingAccount(request.getReceivingAccount())
                .transactionTime(request.getTransactionTime())
                .payload(writePayload(request))
                .build();
        transactionRepository.save(transaction);
        payment.setStatus(PaymentStatus.SUCCESS);
        paymentRepository.save(payment);
        lifecycleService.tryTransition(payment.getOrder(), FulfillmentStatus.CONFIRMED,
                providerName + " payment reconciled");
        event.setProcessed(true);
        event.setProcessedAt(Instant.now());
    }

    @Override
    @Transactional
    public PaymentQrResponse createGatewayPayment(Integer orderId, PaymentProviderType provider) {
        if (provider == PaymentProviderType.VIETQR) {
            return createOrGetPayment(orderId);
        }
        Order order = orderRepository.findByIdForUpdate(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        Payment payment = findCurrentPayment(orderId);
        if (payment != null) {
            if (payment.getStatus() == PaymentStatus.SUCCESS) {
                throw new AppException(ErrorCode.PAYMENT_ALREADY_SUCCESSFUL);
            }
            if (payment.getProvider() != provider) {
                payment.setStatus(PaymentStatus.CANCELLED);
                paymentRepository.save(payment);
            } else if (payment.getStatus() == PaymentStatus.PENDING) {
                // Reuse an unexpired attempt and regenerate the provider request below.
            } else {
                payment = null;
            }
        }
        if (payment == null || payment.getStatus() == PaymentStatus.CANCELLED) {
            payment = Payment.builder()
                    .order(order)
                    .transactionReference("ORD-" + orderId + "-"
                            + UUID.randomUUID().toString().replace("-", "").substring(0, 16))
                    .amount(BigDecimal.valueOf(order.getTotalPrice()))
                    .receivingAccount(null)
                    .provider(provider)
                    .status(PaymentStatus.PENDING)
                    .expiresAt(Instant.now().plus(Duration.ofMinutes(15)))
                    .build();
            payment = paymentRepository.saveAndFlush(payment);
        }
        OnlinePaymentProvider gateway = onlinePaymentProviders.stream()
                .filter(item -> item.type() == provider)
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_PROVIDER_UNAVAILABLE));
        PaymentInitiation initiation = gateway.initiate(payment);
        return PaymentQrResponse.builder()
                .paymentId(payment.getId())
                .orderId(orderId)
                .transactionReference(payment.getTransactionReference())
                .providerTransactionReference(payment.getProviderTransactionReference())
                .providerTransactionNo(payment.getProviderTransactionNo())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .expiresAt(payment.getExpiresAt())
                .paymentUrl(initiation.paymentUrl())
                .provider(provider.name())
                .build();
    }

    private Payment findCurrentPayment(Integer orderId) {
        List<Payment> payments = paymentRepository.findByOrderIdOrderByCreatedAtDesc(orderId);
        if (payments == null) {
            return null;
        }
        Instant now = Instant.now();
        for (Payment payment : payments) {
            if (payment.getStatus() == PaymentStatus.SUCCESS) {
                return payment;
            }
            if (payment.getStatus() == PaymentStatus.PENDING) {
                if (payment.getExpiresAt() != null && payment.getExpiresAt().isAfter(now)) {
                    return payment;
                }
                payment.setStatus(PaymentStatus.EXPIRED);
                paymentRepository.save(payment);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void reconcileGateway(PaymentProviderType provider, PaymentNotificationRequest request) {
        Payment payment = paymentRepository.findByTransactionReference(
                request.getTransactionReference())
                .orElseThrow(() -> new AppException(ErrorCode.TRANSACTION_NOT_FOUND));
        if (payment.getProvider() != provider) {
            throw new AppException(ErrorCode.PAYMENT_PROVIDER_MISMATCH);
        }
        OnlinePaymentProvider gateway = onlinePaymentProviders.stream()
                .filter(item -> item.type() == provider)
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_PROVIDER_UNAVAILABLE));
        if (!gateway.verifyCallback(request)) {
            throw new AppException(ErrorCode.INVALID_PAYMENT_CALLBACK);
        }
        reconcileInternal(request, provider.name());
    }

    @Override
    @Transactional
    public void reconcileVnpay(Map<String, String> parameters) {
        OnlinePaymentProvider gateway = findGateway(PaymentProviderType.VNPAY);
        Map<String, Object> payload = new HashMap<>(parameters);
        if (!gateway.verifyProviderCallback(payload)
                || !gateway.isSuccessfulCallback(payload)) {
            throw new AppException(ErrorCode.INVALID_PAYMENT_CALLBACK);
        }
        reconcileInternal(gateway.normalizeCallback(payload), PaymentProviderType.VNPAY.name());
    }

    @Override
    public Map<String, String> handleVnpayIpn(Map<String, String> parameters) {
        String transactionReference = parameters.getOrDefault("vnp_TxnRef", "");
        if (transactionReference.isBlank()
                || parameters.get("vnp_SecureHash") == null
                || parameters.get("vnp_ResponseCode") == null
                || parameters.get("vnp_TransactionStatus") == null
                || parameters.get("vnp_Amount") == null) {
            return Map.of(
                    "RspCode", "99",
                    "Message", "Missing required VNPay parameters");
        }
        try {
            reconcileVnpay(parameters);
            return Map.of(
                    "RspCode", "00",
                    "Message", "Confirm Success");
        } catch (AppException exception) {
            ErrorCode error = exception.getErrorCode();
            if (error == ErrorCode.INVALID_PAYMENT_CALLBACK
                    || error == ErrorCode.PAYMENT_CHECKSUM_INVALID) {
                return Map.of("RspCode", "97", "Message", "Invalid Checksum");
            }
            if (error == ErrorCode.TRANSACTION_NOT_FOUND) {
                return Map.of("RspCode", "01", "Message", "Order not found");
            }
            if (error == ErrorCode.PAYMENT_ALREADY_SUCCESSFUL) {
                return Map.of("RspCode", "02", "Message", "Order already confirmed");
            }
            if (error == ErrorCode.PAYMENT_AMOUNT_INVALID
                    || error == ErrorCode.PAYMENT_DATA_MISMATCH) {
                return Map.of("RspCode", "04", "Message", "Invalid amount");
            }
            return Map.of("RspCode", "99", "Message", "Unknown error");
        } catch (IllegalArgumentException exception) {
            return Map.of("RspCode", "04", "Message", "Invalid amount");
        } catch (Exception exception) {
            return Map.of("RspCode", "99", "Message", "Unknown error");
        }
    }

    private OnlinePaymentProvider findGateway(PaymentProviderType provider) {
        return onlinePaymentProviders.stream()
                .filter(item -> item.type() == provider)
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_PROVIDER_UNAVAILABLE));
    }

    private PaymentQrResponse toResponse(Payment payment) {
        QrData qr = vietQrProvider.createQr(payment);
        return PaymentQrResponse.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrder().getId())
                .transactionReference(payment.getTransactionReference())
                .providerTransactionReference(payment.getProviderTransactionReference())
                .providerTransactionNo(payment.getProviderTransactionNo())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .expiresAt(payment.getExpiresAt())
                .qrData(qr.data())
                .qrImageUrl(qr.imageUrl())
                .provider(payment.getProvider().name())
                .build();
    }

    private void validateChecksum(PaymentNotificationRequest request) {
        if (properties.getWebhookSecret() == null || properties.getWebhookSecret().isBlank()) {
            if (properties.isEnabled()) {
                throw new AppException(ErrorCode.PAYMENT_CONFIGURATION_INVALID);
            }
            return;
        }
        String canonical = request.getNotificationId() + "|"
                + request.getTransactionId() + "|"
                + request.getTransactionReference() + "|"
                + request.getAmount().toPlainString() + "|"
                + request.getReceivingAccount();
        String expected = HexFormat.of().formatHex(hash(properties.getWebhookSecret() + canonical));
        if (request.getChecksum() == null
                || !MessageDigest.isEqual(expected.getBytes(StandardCharsets.UTF_8),
                        request.getChecksum().getBytes(StandardCharsets.UTF_8))) {
            throw new AppException(ErrorCode.PAYMENT_CHECKSUM_INVALID);
        }
    }

    private byte[] hash(String value) {
        try {
            return MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
        } catch (Exception exception) {
            throw new AppException(ErrorCode.PAYMENT_CHECKSUM_INVALID, exception);
        }
    }

    private String writePayload(PaymentNotificationRequest request) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("notificationId", request.getNotificationId());
            payload.put("transactionId", request.getTransactionId());
            payload.put("transactionReference", request.getTransactionReference());
            payload.put("amount", request.getAmount());
            payload.put("receivingAccount", request.getReceivingAccount());
            payload.put("transactionTime", request.getTransactionTime());
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException exception) {
            throw new AppException(ErrorCode.PAYMENT_SERIALIZATION_FAILED, exception);
        }
    }
}
