package J2EE.com.example.project.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponse {

    private final Integer id;
    private final String name;
    private final String image;
    private final Float price;
    private final String description;
    private final Integer brandId;
    private final String brandName;
    private final Integer categoryId;
    private final String categoryName;
    private final Integer supplierId;
    private final String supplierName;
    private final Integer stock;
    private final Integer status;
    private final Integer viewer;
    private final List<VariantResponse> variants;
}