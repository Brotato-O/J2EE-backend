package J2EE.com.example.project.dto.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartResponse {
    private Integer cartId;
    private List<CartItemResponse> items;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
}