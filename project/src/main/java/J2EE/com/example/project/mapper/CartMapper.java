package J2EE.com.example.project.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import J2EE.com.example.project.dto.response.CartItemResponse;
import J2EE.com.example.project.entity.CartItem;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "variantId", source = "variant.id")
    @Mapping(target = "sku", source = "variant.sku")
    @Mapping(target = "productId", source = "variant.product.id")
    @Mapping(target = "productName", source = "variant.product.name")
    @Mapping(target = "image", source = "variant.product.image")
    @Mapping(target = "color", source = "variant.color.name")
    @Mapping(target = "size", source = "variant.size.name")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "availableStock", source = "variant.stockQuantity")
    @Mapping(target = "unitPrice", source = "price")
    @Mapping(target = "totalPrice", source = "totalPrice")
    CartItemResponse toResponse(CartItem item);

    List<CartItemResponse> toResponseList(List<CartItem> items);
}