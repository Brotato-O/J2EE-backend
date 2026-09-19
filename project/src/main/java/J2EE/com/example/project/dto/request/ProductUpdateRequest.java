package J2EE.com.example.project.dto.request;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductUpdateRequest {

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 255, message = "Tên sản phẩm không được vượt quá 255 ký tự")
    private String name;

    @NotBlank(message = "Ảnh sản phẩm không được để trống")
    @Size(max = 255, message = "Ảnh sản phẩm không được vượt quá 255 ký tự")
    private String image;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @Min(value = 0, message = "Giá sản phẩm không được nhỏ hơn 0")
    private Float price;

    @NotBlank(message = "Mô tả sản phẩm không được để trống")
    private String description;

    @NotNull(message = "Thương hiệu không được để trống")
    private Integer brandId;

    @NotNull(message = "Danh mục không được để trống")
    private Integer categoryId;

    @NotNull(message = "Nhà cung cấp không được để trống")
    private Integer supplierId;

    @NotNull(message = "Tồn kho sản phẩm không được để trống")
    @Min(value = 0, message = "Tồn kho sản phẩm không được nhỏ hơn 0")
    private Integer stock;

    @Valid
    @NotNull(message = "Danh sách biến thể không được để trống")
    private List<VariantRequest> variants = new ArrayList<>();
}