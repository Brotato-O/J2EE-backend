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

    @NotBlank(message = "Tỉnh/thành phố không được để trống")
    private String recipientProvince;

    @NotBlank(message = "Quận/huyện không được để trống")
    private String recipientDistrict;

    @NotBlank(message = "Phường/xã không được để trống")
    private String recipientWard;

    private PaymentMethod paymentMethod = PaymentMethod.VIETQR;
}