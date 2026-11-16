package J2EE.com.example.project.configuration;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.ghtk")
public class GhtkProperties {
    private boolean enabled;
    private String baseUrl;
    private String apiToken;
    private String clientSource;
    private String createOrderUrl;
    private String trackingUrlTemplate;
    private String cancelUrlTemplate;
    private String webhookHash;
    private Duration connectTimeout = Duration.ofSeconds(3);
    private Duration readTimeout = Duration.ofSeconds(10);
    private int maxAttempts = 3;
}
