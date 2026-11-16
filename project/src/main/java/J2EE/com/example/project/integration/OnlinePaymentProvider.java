package J2EE.com.example.project.integration;

import J2EE.com.example.project.entity.Payment;
import J2EE.com.example.project.enums.PaymentProviderType;
import J2EE.com.example.project.dto.request.PaymentNotificationRequest;

public interface OnlinePaymentProvider {
    PaymentProviderType type();
    PaymentInitiation initiate(Payment payment);
    boolean verifyCallback(PaymentNotificationRequest request);
}
