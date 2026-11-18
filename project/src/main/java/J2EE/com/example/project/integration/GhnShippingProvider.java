package J2EE.com.example.project.integration;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import J2EE.com.example.project.configuration.GhnProperties;
import J2EE.com.example.project.entity.Order;
import J2EE.com.example.project.entity.OrderDetail;
import J2EE.com.example.project.entity.Shipment;
import J2EE.com.example.project.enums.PaymentMethod;
import J2EE.com.example.project.enums.ShippingProviderType;
import J2EE.com.example.project.exception.AppException;
import J2EE.com.example.project.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GhnShippingProvider implements ShippingProvider {
    private final GhnProperties properties;

    @Override
    public ShippingProviderType type() {
        return ShippingProviderType.GHN;
    }

    @Override
    public ShippingCreateResult createShipment(Order order, Shipment shipment) {
        requireConfigured();
        Map<String, Object> response = execute(() -> client().post()
                .uri(properties.getCreateOrderUrl())
                .headers(headers -> headers.set("Token", properties.getApiToken()))
                .headers(headers -> headers.set("ShopId", String.valueOf(properties.getShopId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(buildCreatePayload(order))
                .retrieve().body(Map.class));
        Map<?, ?> data = response.get("data") instanceof Map<?, ?> value ? value : Map.of();
        String orderCode = text(data.get("order_code"));
        if (orderCode.isBlank()) {
            throw new AppException(ErrorCode.SHIPPING_RESPONSE_INVALID);
        }
        return new ShippingCreateResult(orderCode, orderCode);
    }

    @Override
    public ShippingStatusResult getStatus(String trackingCode) {
        requireConfigured();
        Map<String, Object> response = execute(() -> client().post()
                .uri(properties.getDetailOrderUrl())
                .headers(headers -> headers.set("Token", properties.getApiToken()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("order_code", trackingCode))
                .retrieve().body(Map.class));
        Map<?, ?> data = response.get("data") instanceof Map<?, ?> value ? value : Map.of();
        return new ShippingStatusResult(0, text(data.get("status")));
    }

    @Override
    public void cancel(String trackingCode) {
        requireConfigured();
        execute(() -> client().post()
                .uri(properties.getCancelOrderUrl())
                .headers(headers -> {
                    headers.set("Token", properties.getApiToken());
                    headers.set("ShopId", String.valueOf(properties.getShopId()));
                })
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("order_codes", List.of(trackingCode)))
                .retrieve().body(Map.class));
    }

    private Map<String, Object> buildCreatePayload(Order order) {
        if (isBlank(order.getRecipientProvince()) || isBlank(order.getRecipientDistrict())
                || isBlank(order.getRecipientWard())) {
            throw new AppException(ErrorCode.SHIPPING_ADDRESS_INCOMPLETE);
        }
        Map<String, Object> payload = new HashMap<>();
        payload.put("client_order_code", "ORDER-" + order.getId());
        payload.put("to_name", order.getKhachHang().getName());
        payload.put("to_phone", order.getKhachHang().getPhone());
        payload.put("to_address", order.getAddress());
        payload.put("to_ward_name", order.getRecipientWard());
        payload.put("to_district_name", order.getRecipientDistrict());
        payload.put("to_province_name", order.getRecipientProvince());
        payload.put("from_name", properties.getFromName());
        payload.put("from_phone", properties.getFromPhone());
        payload.put("from_address", properties.getFromAddress());
        payload.put("from_ward_name", properties.getFromWardName());
        payload.put("from_district_name", properties.getFromDistrictName());
        payload.put("from_province_name", properties.getFromProvinceName());
        payload.put("service_type_id", properties.getServiceTypeId());
        payload.put("payment_type_id", order.getPaymentMethod() == PaymentMethod.COD
                ? properties.getPaymentTypeId() : 1);
        payload.put("cod_amount", order.getPaymentMethod() == PaymentMethod.COD ? order.getTotalPrice() : 0);
        payload.put("order_value", order.getTotalPrice());
        payload.put("required_note", properties.getRequiredNote());
        payload.put("weight", properties.getDefaultWeightGrams());
        payload.put("length", properties.getDefaultLengthCm());
        payload.put("width", properties.getDefaultWidthCm());
        payload.put("height", properties.getDefaultHeightCm());
        payload.put("items", order.getDetails().stream().map(this::buildItem).toList());
        return payload;
    }

    private Map<String, Object> buildItem(OrderDetail detail) {
        return Map.of("name", detail.getProduct().getName(),
                "code", "PRODUCT-" + detail.getProduct().getId(),
                "price", detail.getPrice(),
                "quantity", detail.getQuantity(),
                "length", properties.getDefaultLengthCm(),
                "width", properties.getDefaultWidthCm(),
                "height", properties.getDefaultHeightCm(),
                "weight", properties.getDefaultWeightGrams());
    }

    private RestClient client() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(properties.getConnectTimeout());
        factory.setReadTimeout(properties.getReadTimeout());
        return RestClient.builder().baseUrl(properties.getBaseUrl()).requestFactory(factory).build();
    }

    private <T> T execute(java.util.function.Supplier<T> request) {
        RestClientException last = null;
        for (int attempt = 1; attempt <= properties.getMaxAttempts(); attempt++) {
            try {
                T response = request.get();
                if (response instanceof Map<?, ?> map && !Integer.valueOf(200).equals(map.get("code"))) {
                    throw new AppException(ErrorCode.SHIPPING_PROVIDER_UNAVAILABLE);
                }
                return response;
            } catch (RestClientException exception) {
                last = exception;
            }
        }
        throw new AppException(ErrorCode.SHIPPING_REQUEST_FAILED, last);
    }

    private void requireConfigured() {
        if (!properties.isEnabled() || isBlank(properties.getApiToken()) || properties.getShopId() == null) {
            throw new AppException(ErrorCode.SHIPPING_PROVIDER_UNAVAILABLE);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private String text(Object value) {
        return value == null ? "" : String.valueOf(value);
    }
}
