package J2EE.com.example.project.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@Setter
public class ProductResponse {

    private Integer id;
    private  String name;
    private  String image;
    private  Float price;
    private  String description;
    private  Integer brandId;
    private  String brandName;
    private  Integer categoryId;
    private  String categoryName;
    private  Integer supplierId;
    private  String supplierName;
    private  Integer stock;
    private  Integer status;
    private  Integer viewer;
    private  List<VariantResponse> variants;
}