package J2EE.com.example.project.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import J2EE.com.example.project.dto.response.OrderDetailResponse;
import J2EE.com.example.project.dto.response.OrderResponse;
import J2EE.com.example.project.entity.Order;
import J2EE.com.example.project.entity.OrderDetail;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "id", target = "orderId")
    @Mapping(source = "khachHang.id", target = "customerId")
    @Mapping(source = "khachHang.name", target = "customerName")
    @Mapping(source = "details", target = "items")
    OrderResponse toResponse(Order order);

    List<OrderResponse> toResponseList(List<Order> orders);

    @Mapping(source = "id", target = "orderDetailId")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "color.name", target = "colorName") // Color.name (color_name)
    @Mapping(source = "size.name", target = "sizeName") // Size.name (size_name)
    OrderDetailResponse toDetailResponse(OrderDetail detail);

    List<OrderDetailResponse> toDetailResponseList(List<OrderDetail> details);
}
