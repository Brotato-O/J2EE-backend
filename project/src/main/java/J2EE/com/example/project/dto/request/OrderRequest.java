package J2EE.com.example.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import J2EE.com.example.project.enums.PaymentMethod;

@Getter
@Setter
public class OrderRequest {

    @NotBlank(message = "Địa chỉ giao hàng không được để trống")
    private String address;

    private PaymentMethod paymentMethod = PaymentMethod.VIETQR;
}
