package J2EE.com.example.project.configuration;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.vietqr")
public class VietQrProperties {
    private boolean enabled;
    private String apiBaseUrl;
    private String apiToken;
    private String secretKey;
    private String bankBin;
    private String bankAccount;
    private String accountName;
    private String terminalCode;
    private String qrImageUrlTemplate;
    private String webhookSecret;
    private Duration expiration = Duration.ofMinutes(15);
}
