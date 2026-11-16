package J2EE.com.example.project.integration;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import J2EE.com.example.project.entity.Payment;

@Component
public class MockVietQrProvider implements VietQrProvider {
    @Override
    public QrData createQr(Payment payment) {
        String data = "MOCK-VIETQR|reference=" + payment.getTransactionReference()
                + "|amount=" + payment.getAmount().toPlainString();
        return new QrData(data, data);
    }
}
