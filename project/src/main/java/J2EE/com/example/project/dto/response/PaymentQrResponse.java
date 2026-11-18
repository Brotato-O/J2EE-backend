package J2EE.com.example.project.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

import J2EE.com.example.project.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentQrResponse {
    private Long paymentId;
    private Integer orderId;
    private String transactionReference;
    private String providerTransactionReference;
    private String providerTransactionNo;
    private BigDecimal amount;
    private PaymentStatus status;
    private Instant expiresAt;
    private String qrData;
    private String qrImageUrl;
    private String paymentUrl;
    private String provider;
}
