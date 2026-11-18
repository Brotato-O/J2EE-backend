package J2EE.com.example.project.service;

import J2EE.com.example.project.dto.request.PaymentNotificationRequest;
import J2EE.com.example.project.dto.response.PaymentQrResponse;
import J2EE.com.example.project.enums.PaymentProviderType;
import java.util.Map;

public interface PaymentService {
    PaymentQrResponse createOrGetPayment(Integer orderId);
    void reconcile(PaymentNotificationRequest request);
    PaymentQrResponse createGatewayPayment(Integer orderId, PaymentProviderType provider);
    void reconcileGateway(PaymentProviderType provider, PaymentNotificationRequest request);
    void reconcileVnpay(Map<String, String> parameters);
    Map<String, String> handleVnpayIpn(Map<String, String> parameters);
}
