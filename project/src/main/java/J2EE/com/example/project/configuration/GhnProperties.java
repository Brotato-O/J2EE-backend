package J2EE.com.example.project.configuration;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.ghn")
public class GhnProperties {
    private boolean enabled;
    private String baseUrl = "https://dev-online-gateway.ghn.vn";
    private String apiToken;
    private Integer shopId;
    private String createOrderUrl = "/shiip/public-api/v2/shipping-order/create";
    private String detailOrderUrl = "/shiip/public-api/v2/shipping-order/detail";
    private String cancelOrderUrl = "/shiip/public-api/v2/switch-status/cancel";
    private String webhookSecret;
    private String fromName;
    private String fromPhone;
    private String fromAddress;
    private String fromWardName;
    private String fromDistrictName;
    private String fromProvinceName;
    private Integer serviceTypeId = 2;
    private Integer paymentTypeId = 1;
    private String requiredNote = "KHONGCHOXEMHANG";
    private Integer defaultWeightGrams = 500;
    private Integer defaultLengthCm = 20;
    private Integer defaultWidthCm = 15;
    private Integer defaultHeightCm = 10;
    private Duration connectTimeout = Duration.ofSeconds(3);
    private Duration readTimeout = Duration.ofSeconds(10);
    private int maxAttempts = 3;
}
