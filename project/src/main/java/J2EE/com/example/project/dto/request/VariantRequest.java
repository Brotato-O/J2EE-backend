package J2EE.com.example.project.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VariantRequest {

    // Null khi tạo mới, có giá trị khi cập nhật
    private Integer id;

    @NotNull(message = "Màu sắc không được để trống")
    @Positive(message = "ID màu sắc phải lớn hơn 0")
    private Integer colorId;

    @NotNull(message = "Kích thước không được để trống")
    @Positive(message = "ID kích thước phải lớn hơn 0")
    private Integer sizeId;

    @NotNull(message = "Số lượng tồn kho không được để trống")
    @Min(value = 0, message = "Số lượng tồn kho không được nhỏ hơn 0")
    private Integer stockQuantity;

    @Size(max = 100, message = "SKU không được vượt quá 100 ký tự")
    private String sku;
}