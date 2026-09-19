package J2EE.com.example.project.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VariantRequest {

    private Integer id;

    @NotNull(message = "Màu sắc không được để trống")
    private Integer colorId;

    @NotNull(message = "Kích thước không được để trống")
    private Integer sizeId;

    @Min(value = 0, message = "Giá biến thể không được nhỏ hơn 0")
    private Float price;

    @NotNull(message = "Số lượng tồn kho không được để trống")
    @Min(value = 0, message = "Số lượng tồn kho không được nhỏ hơn 0")
    private Integer stockQuantity;

    private String sku;
}