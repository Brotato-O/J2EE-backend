package J2EE.com.example.project.integration;

import J2EE.com.example.project.entity.Payment;

public interface VietQrProvider {
    QrData createQr(Payment payment);
}
