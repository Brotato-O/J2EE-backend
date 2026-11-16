package J2EE.com.example.project.service;

import J2EE.com.example.project.dto.request.PaymentNotificationRequest;
import J2EE.com.example.project.dto.response.PaymentQrResponse;
import J2EE.com.example.project.enums.PaymentProviderType;

public interface PaymentService {
    PaymentQrResponse createOrGetPayment(Integer orderId);
    void reconcile(PaymentNotificationRequest request);
    PaymentQrResponse createGatewayPayment(Integer orderId, PaymentProviderType provider);
    void reconcileGateway(PaymentProviderType provider, PaymentNotificationRequest request);
}
