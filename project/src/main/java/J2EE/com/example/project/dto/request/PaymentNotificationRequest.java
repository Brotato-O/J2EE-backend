package J2EE.com.example.project.dto.request;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentNotificationRequest {
    @NotBlank
    private String notificationId;
    @NotBlank
    private String transactionId;
    @NotBlank
    private String transactionReference;
    @NotNull
    private BigDecimal amount;
    private String receivingAccount;
    private Instant transactionTime;
    private String checksum;
}
