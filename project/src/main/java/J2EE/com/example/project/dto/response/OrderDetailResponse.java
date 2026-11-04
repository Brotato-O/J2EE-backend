package J2EE.com.example.project.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {

    private Integer orderDetailId;
    private Integer productId;
    private String productName;
    private String colorName;
    private String sizeName;
    private Integer price;
    private Integer quantity;
    private Integer totalPrice;
}
