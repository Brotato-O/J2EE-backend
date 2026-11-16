package J2EE.com.example.project.integration;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import J2EE.com.example.project.configuration.VietQrProperties;
import J2EE.com.example.project.entity.Payment;
import J2EE.com.example.project.exception.AppException;
import J2EE.com.example.project.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@Component
@Primary
@RequiredArgsConstructor
public class VietQrProviderImpl implements VietQrProvider {

    private final VietQrProperties properties;

    @Override
    public QrData createQr(Payment payment) {
        requireConfigured();
        String reference = encode(payment.getTransactionReference());
        String amount = payment.getAmount().toPlainString();
        String accountName = encode(properties.getAccountName());
        String data = "bankBin=" + properties.getBankBin()
                + "&accountNumber=" + properties.getBankAccount()
                + "&amount=" + amount
                + "&addInfo=" + reference
                + "&accountName=" + accountName;
        String imageUrl = properties.getQrImageUrlTemplate()
                .replace("{bankBin}", encode(properties.getBankBin()))
                .replace("{accountNumber}", encode(properties.getBankAccount()))
                .replace("{amount}", amount)
                .replace("{reference}", reference)
                .replace("{accountName}", accountName);
        return new QrData(data, imageUrl);
    }

    private void requireConfigured() {
        if (properties.getBankBin() == null || properties.getBankBin().isBlank()
                || properties.getBankAccount() == null || properties.getBankAccount().isBlank()) {
            throw new AppException(ErrorCode.VIETQR_CONFIGURATION_INVALID);
        }
    }

    private String encode(String value) {
        return URLEncoder.encode(value == null ? "" : value, StandardCharsets.UTF_8);
    }
}
