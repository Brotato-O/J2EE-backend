package J2EE.com.example.project.dto.response;

import java.util.List;

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
public class OrderResponse {

    private Integer orderId;
    private Integer customerId;
    private String customerName;
    private String orderDate;
    private String status;
    private String address;
    private Integer totalPrice;
    private List<OrderDetailResponse> items;
}
