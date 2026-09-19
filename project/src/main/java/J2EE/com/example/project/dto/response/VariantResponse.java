package J2EE.com.example.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class VariantResponse {

    private final Integer id;
    private final Integer colorId;
    private final String colorName;
    private final Integer sizeId;
    private final String sizeName;
    private final Float price;
    private final Integer stockQuantity;
    private final String sku;
}