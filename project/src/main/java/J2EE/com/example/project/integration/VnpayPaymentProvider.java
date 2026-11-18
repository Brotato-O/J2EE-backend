package J2EE.com.example.project.integration;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.time.Instant;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import J2EE.com.example.project.configuration.VnpayProperties;
import J2EE.com.example.project.dto.request.PaymentNotificationRequest;
import J2EE.com.example.project.entity.Payment;
import J2EE.com.example.project.enums.PaymentProviderType;
import J2EE.com.example.project.exception.AppException;
import J2EE.com.example.project.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VnpayPaymentProvider implements OnlinePaymentProvider {

    private final VnpayProperties properties;

    @Override
    public PaymentProviderType type() {
        return PaymentProviderType.VNPAY;
    }

    @Override
    public PaymentInitiation initiate(Payment payment) {
        if (!properties.isEnabled()) {
            return new PaymentInitiation("MOCK-VNPAY://" + payment.getTransactionReference(),
                    payment.getTransactionReference());
        }

        requireConfigured();
        Map<String, String> params = new TreeMap<>();
        params.put("vnp_Version", "2.1.0");
        params.put("vnp_Command", "pay");
        params.put("vnp_TmnCode", properties.getTmnCode());
        params.put("vnp_Amount", payment.getAmount().movePointRight(2).toBigInteger().toString());
        params.put("vnp_CurrCode", "VND");
        params.put("vnp_TxnRef", payment.getTransactionReference());
        params.put("vnp_OrderInfo", "Thanh toan don hang " + payment.getOrder().getId());
        params.put("vnp_OrderType", "other");
        params.put("vnp_Locale", "vn");
        params.put("vnp_ReturnUrl", properties.getReturnUrl());
        params.put("vnp_IpAddr", "127.0.0.1");
        params.put("vnp_CreateDate", ZonedDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        String query = params.entrySet().stream()
                .map(entry -> encode(entry.getKey()) + "=" + encode(entry.getValue()))
                .reduce((left, right) -> left + "&" + right).orElse("");
        return new PaymentInitiation(properties.getPaymentUrl() + "?" + query
                + "&vnp_SecureHash=" + hmacSha512(properties.getHashSecret(), query),
                payment.getTransactionReference());
    }

    @Override
    public boolean verifyCallback(PaymentNotificationRequest request) {
        return verify(request, properties.getWebhookSecret(), "HmacSHA512");
    }

    @Override
    public PaymentNotificationRequest normalizeCallback(Map<String, ?> payload) {
        String transactionReference = value(payload, "vnp_TxnRef");
        String transactionId = value(payload, "vnp_TransactionNo");
        String responseCode = value(payload, "vnp_ResponseCode");
        PaymentNotificationRequest request = new PaymentNotificationRequest();
        request.setNotificationId(transactionId);
        request.setTransactionId(transactionId);
        request.setTransactionReference(transactionReference);
        request.setAmount(new java.math.BigDecimal(value(payload, "vnp_Amount"))
                .movePointLeft(2));
        request.setReceivingAccount(value(payload, "vnp_BankCode"));
        request.setChecksum(value(payload, "vnp_SecureHash"));
        request.setTransactionTime(Instant.now());
        request.setProviderStatus(responseCode);
        return request;
    }

    @Override
    public boolean verifyProviderCallback(Map<String, ?> payload) {
        String received = value(payload, "vnp_SecureHash");
        if (isBlank(received) || isBlank(properties.getHashSecret())) {
            return false;
        }
        Map<String, String> signed = new TreeMap<>();
        payload.forEach((key, value) -> {
            if (key.startsWith("vnp_")
                    && !"vnp_SecureHash".equals(key)
                    && !"vnp_SecureHashType".equals(key)
                    && value != null
                    && !String.valueOf(value).isBlank()) {
                signed.put(key, String.valueOf(value));
            }
        });
        String query = signed.entrySet().stream()
                .map(entry -> encode(entry.getKey()) + "=" + encode(entry.getValue()))
                .reduce((left, right) -> left + "&" + right).orElse("");
        return hmacSha512(properties.getHashSecret(), query).equalsIgnoreCase(received);
    }

    @Override
    public boolean isSuccessfulCallback(Map<String, ?> payload) {
        return "00".equals(value(payload, "vnp_ResponseCode"))
                && ("00".equals(value(payload, "vnp_TransactionStatus"))
                        || isBlank(value(payload, "vnp_TransactionStatus")));
    }

    private void requireConfigured() {
        if (isBlank(properties.getTmnCode()) || isBlank(properties.getHashSecret())
                || isBlank(properties.getReturnUrl())) {
            throw new AppException(ErrorCode.PAYMENT_CONFIGURATION_INVALID);
        }
    }

    private String hmacSha512(String secret, String value) {
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA512"));
            StringBuilder result = new StringBuilder();
            for (byte item : mac.doFinal(value.getBytes(StandardCharsets.UTF_8))) {
                result.append(String.format("%02x", item));
            }
            return result.toString();
        } catch (Exception exception) {
            throw new AppException(ErrorCode.PAYMENT_SERIALIZATION_FAILED, exception);
        }
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private boolean verify(PaymentNotificationRequest request, String secret, String algorithm) {
        if (isBlank(secret) || isBlank(request.getChecksum())) {
            return false;
        }
        String canonical = request.getNotificationId() + "|"
                + request.getTransactionId() + "|" + request.getTransactionReference() + "|"
                + request.getAmount().toPlainString() + "|" + request.getReceivingAccount();
        return hmac(canonical, secret, algorithm).equalsIgnoreCase(request.getChecksum());
    }

    private String value(Map<String, ?> payload, String key) {
        Object value = payload.get(key);
        return value == null ? "" : String.valueOf(value);
    }

    private String hmac(String value, String secret, String algorithm) {
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), algorithm));
            StringBuilder result = new StringBuilder();
            for (byte item : mac.doFinal(value.getBytes(StandardCharsets.UTF_8))) {
                result.append(String.format("%02x", item));
            }
            return result.toString();
        } catch (Exception exception) {
            throw new AppException(ErrorCode.INVALID_PAYMENT_CALLBACK, exception);
        }
    }
}
