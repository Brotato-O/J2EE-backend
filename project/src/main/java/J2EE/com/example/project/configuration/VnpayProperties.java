package J2EE.com.example.project.configuration;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.vnpay")
public class VnpayProperties {
    private boolean enabled;
    private String paymentUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private String tmnCode;
    private String hashSecret;
    private String returnUrl;
    private String ipnUrl;
    private String webhookSecret;
    private Duration expiration = Duration.ofMinutes(15);
}
