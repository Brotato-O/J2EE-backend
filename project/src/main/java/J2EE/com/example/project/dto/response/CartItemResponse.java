package J2EE.com.example.project.dto.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CartItemResponse {
    private Integer id;
    private Integer variantId;
    private String sku;
    private Integer productId;
    private String productName;
    private String image;
    private String color;
    private String size;
    private Integer quantity;
    private Integer availableStock;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
}