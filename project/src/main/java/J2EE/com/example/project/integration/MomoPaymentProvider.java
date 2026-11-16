package J2EE.com.example.project.integration;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import J2EE.com.example.project.configuration.MomoProperties;
import J2EE.com.example.project.dto.request.PaymentNotificationRequest;
import J2EE.com.example.project.entity.Payment;
import J2EE.com.example.project.enums.PaymentProviderType;
import J2EE.com.example.project.exception.AppException;
import J2EE.com.example.project.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MomoPaymentProvider implements OnlinePaymentProvider {

    private final MomoProperties properties;

    @Override
    public PaymentProviderType type() {
        return PaymentProviderType.MOMO;
    }

    @Override
    public PaymentInitiation initiate(Payment payment) {
        if (!properties.isEnabled()) {
            return new PaymentInitiation("MOCK-MOMO://" + payment.getTransactionReference(),
                    payment.getTransactionReference());
        }

        requireConfigured();
        String requestId = UUID.randomUUID().toString();
        String orderId = payment.getTransactionReference();
        String amount = payment.getAmount().toBigInteger().toString();
        String requestType = "captureWallet";
        String rawSignature = "accessKey=" + properties.getAccessKey()
                + "&amount=" + amount
                + "&extraData=&ipnUrl=" + properties.getIpnUrl()
                + "&orderId=" + orderId
                + "&orderInfo=Thanh toan don hang " + payment.getOrder().getId()
                + "&partnerCode=" + properties.getPartnerCode()
                + "&redirectUrl=" + properties.getRedirectUrl()
                + "&requestId=" + requestId
                + "&requestType=" + requestType;
        Map<String, Object> request = new HashMap<>();
        request.put("partnerCode", properties.getPartnerCode());
        request.put("requestId", requestId);
        request.put("amount", amount);
        request.put("orderId", orderId);
        request.put("orderInfo", "Thanh toan don hang " + payment.getOrder().getId());
        request.put("redirectUrl", properties.getRedirectUrl());
        request.put("ipnUrl", properties.getIpnUrl());
        request.put("requestType", requestType);
        request.put("extraData", "");
        request.put("signature", hmacSha256(properties.getSecretKey(), rawSignature));
        Map<?, ?> response = RestClient.create().post().uri(properties.getEndpoint())
                .contentType(MediaType.APPLICATION_JSON).body(request).retrieve().body(Map.class);
        Object payUrl = response == null ? null : response.get("payUrl");
        if (payUrl == null) {
            throw new AppException(ErrorCode.PAYMENT_SERIALIZATION_FAILED);
        }
        return new PaymentInitiation(String.valueOf(payUrl), orderId);
    }

    @Override
    public boolean verifyCallback(PaymentNotificationRequest request) {
        if (isBlank(properties.getWebhookSecret()) || isBlank(request.getChecksum())) {
            return false;
        }
        String canonical = request.getNotificationId() + "|"
                + request.getTransactionId() + "|" + request.getTransactionReference() + "|"
                + request.getAmount().toPlainString() + "|" + request.getReceivingAccount();
        return hmacSha256(properties.getWebhookSecret(), canonical)
                .equalsIgnoreCase(request.getChecksum());
    }

    private void requireConfigured() {
        if (isBlank(properties.getPartnerCode()) || isBlank(properties.getAccessKey())
                || isBlank(properties.getSecretKey()) || isBlank(properties.getRedirectUrl())
                || isBlank(properties.getIpnUrl())) {
            throw new AppException(ErrorCode.PAYMENT_CONFIGURATION_INVALID);
        }
    }

    private String hmacSha256(String secret, String value) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            StringBuilder result = new StringBuilder();
            for (byte item : mac.doFinal(value.getBytes(StandardCharsets.UTF_8))) {
                result.append(String.format("%02x", item));
            }
            return result.toString();
        } catch (Exception exception) {
            throw new AppException(ErrorCode.PAYMENT_SERIALIZATION_FAILED, exception);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
