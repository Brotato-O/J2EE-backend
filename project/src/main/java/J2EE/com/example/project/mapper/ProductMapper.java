package J2EE.com.example.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import J2EE.com.example.project.dto.request.ProductCreationRequest;
import J2EE.com.example.project.dto.request.ProductUpdateRequest;
import J2EE.com.example.project.dto.response.ProductResponse;
import J2EE.com.example.project.entity.Product;

@Mapper(componentModel = "spring", uses = VariantMapper.class)
public interface ProductMapper {

    @Mapping(target = "brandId", source = "brand.id")
    @Mapping(target = "brandName", source = "brand.name")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    @Mapping(target = "supplierId", source = "supplier.id")
    @Mapping(target = "supplierName", source = "supplier.name")
    @Mapping(target = "variants", source = "variants")
    ProductResponse toResponse(Product entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brandName", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "viewer", ignore = true)
    @Mapping(target = "variants", ignore = true)
    Product toEntity(ProductCreationRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "brandName", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "supplier", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "viewer", ignore = true)
    @Mapping(target = "variants", ignore = true)
    void updateEntity(ProductUpdateRequest request, @org.mapstruct.MappingTarget Product entity);
}