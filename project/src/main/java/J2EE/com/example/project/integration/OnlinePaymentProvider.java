package J2EE.com.example.project.integration;

import J2EE.com.example.project.entity.Payment;
import J2EE.com.example.project.enums.PaymentProviderType;
import J2EE.com.example.project.dto.request.PaymentNotificationRequest;
import java.util.Map;

public interface OnlinePaymentProvider {
    PaymentProviderType type();
    PaymentInitiation initiate(Payment payment);
    boolean verifyCallback(PaymentNotificationRequest request);

    default PaymentNotificationRequest normalizeCallback(Map<String, ?> payload) {
        throw new UnsupportedOperationException("Provider callback normalization is not supported");
    }

    default boolean verifyProviderCallback(Map<String, ?> payload) {
        throw new UnsupportedOperationException("Provider callback verification is not supported");
    }

    default boolean isSuccessfulCallback(Map<String, ?> payload) {
        return true;
    }
}
