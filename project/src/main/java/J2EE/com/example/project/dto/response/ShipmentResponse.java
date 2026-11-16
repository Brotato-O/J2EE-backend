package J2EE.com.example.project.dto.response;

import J2EE.com.example.project.enums.FulfillmentStatus;
import J2EE.com.example.project.enums.ShipmentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShipmentResponse {
    private Integer orderId;
    private Long fulfillmentId;
    private String provider;
    private String trackingCode;
    private String providerOrderCode;
    private FulfillmentStatus fulfillmentStatus;
    private ShipmentStatus shipmentStatus;
}
