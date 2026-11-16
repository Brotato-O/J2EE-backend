package J2EE.com.example.project.integration;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClient;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import J2EE.com.example.project.configuration.GhtkProperties;
import J2EE.com.example.project.entity.Order;
import J2EE.com.example.project.entity.OrderDetail;
import J2EE.com.example.project.entity.Shipment;
import J2EE.com.example.project.enums.ShippingProviderType;
import J2EE.com.example.project.enums.PaymentMethod;
import J2EE.com.example.project.exception.AppException;
import J2EE.com.example.project.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@Component
@Primary
@RequiredArgsConstructor
public class GhtkShippingProvider implements ShippingProvider {

    private final GhtkProperties properties;
    private final MockShippingProvider mockProvider;

    @Override
    public ShippingProviderType type() {
        return ShippingProviderType.GHTK;
    }

    @Override
    public ShippingCreateResult createShipment(Order order, Shipment shipment) {
        if (!isConfigured()) {
            return mockProvider.createShipment(order, shipment);
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("order", buildOrderPayload(order));
        Map<?, ?> response = null;
        RestClientException lastException = null;
        for (int attempt = 1; attempt <= properties.getMaxAttempts(); attempt++) {
            try {
                response = client().post()
                        .uri(properties.getCreateOrderUrl())
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(headers -> {
                            headers.set("Token", properties.getApiToken());
                            headers.set("X-Client-Source", properties.getClientSource());
                        })
                        .body(payload)
                        .retrieve()
                        .body(Map.class);
                break;
            } catch (RestClientException exception) {
                lastException = exception;
            }
        }
        if (response == null && lastException != null) {
            throw new AppException(ErrorCode.SHIPPING_REQUEST_FAILED, lastException);
        }

        if (response == null || !Boolean.TRUE.equals(response.get("success"))) {
            throw new AppException(ErrorCode.SHIPPING_PROVIDER_UNAVAILABLE);
        }
        Map<?, ?> orderResponse = response.get("order") instanceof Map<?, ?> map ? map : response;
        String label = String.valueOf(orderResponse.get("label"));
        if (label.isBlank() || "null".equals(label)) {
            throw new AppException(ErrorCode.SHIPPING_RESPONSE_INVALID);
        }
        Object partnerId = orderResponse.get("partner_id");
        return new ShippingCreateResult(label, partnerId == null ? label : String.valueOf(partnerId));
    }

    @Override
    public ShippingStatusResult getStatus(String trackingCode) {
        if (!isConfigured()) {
            return mockProvider.getStatus(trackingCode);
        }
        String url = properties.getTrackingUrlTemplate().replace("{trackingCode}", trackingCode);
        Map<?, ?> response = null;
        RestClientException lastException = null;
        for (int attempt = 1; attempt <= properties.getMaxAttempts(); attempt++) {
            try {
                response = client().get()
                        .uri(url)
                        .headers(headers -> {
                            headers.set("Token", properties.getApiToken());
                            headers.set("X-Client-Source", properties.getClientSource());
                        })
                        .retrieve()
                        .body(Map.class);
                break;
            } catch (RestClientException exception) {
                lastException = exception;
            }
        }
        if (response == null && lastException != null) {
            throw new AppException(ErrorCode.SHIPPING_REQUEST_FAILED, lastException);
        }
        if (response == null || !Boolean.TRUE.equals(response.get("success"))) {
            throw new AppException(ErrorCode.SHIPPING_PROVIDER_UNAVAILABLE);
        }
        Map<?, ?> orderResponse = response.get("order") instanceof Map<?, ?> map ? map : response;
        Object status = orderResponse.get("status_id");
        return new ShippingStatusResult(Integer.parseInt(String.valueOf(status)), "GHTK status");
    }

    @Override
    public void cancel(String trackingCode) {
        if (!isConfigured()) {
            mockProvider.cancel(trackingCode);
            return;
        }
        String url = properties.getCancelUrlTemplate().replace("{trackingCode}", trackingCode);
        RestClientException lastException = null;
        for (int attempt = 1; attempt <= properties.getMaxAttempts(); attempt++) {
            try {
                client().post()
                        .uri(url)
                        .headers(headers -> {
                            headers.set("Token", properties.getApiToken());
                            headers.set("X-Client-Source", properties.getClientSource());
                        })
                        .retrieve()
                        .toBodilessEntity();
                return;
            } catch (RestClientException exception) {
                lastException = exception;
            }
        }
        throw new AppException(ErrorCode.SHIPPING_REQUEST_FAILED, lastException);
    }

    private boolean isConfigured() {
        return properties.isEnabled()
                && hasText(properties.getApiToken())
                && hasText(properties.getClientSource())
                && hasText(properties.getCreateOrderUrl())
                && hasText(properties.getTrackingUrlTemplate())
                && hasText(properties.getCancelUrlTemplate());
    }

    private RestClient client() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(properties.getConnectTimeout());
        requestFactory.setReadTimeout(properties.getReadTimeout());
        return RestClient.builder()
                .baseUrl(properties.getBaseUrl() == null ? "" : properties.getBaseUrl())
                .requestFactory(requestFactory)
                .build();
    }

    private Map<String, Object> buildOrderPayload(Order order) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", "ORDER-" + order.getId());
        data.put("name", order.getKhachHang().getName());
        data.put("tel", order.getKhachHang().getPhone());
        data.put("address", order.getAddress());
        data.put("province", "");
        data.put("district", "");
        data.put("pick_money", order.getPaymentMethod() == PaymentMethod.COD
                ? BigDecimal.valueOf(order.getTotalPrice()) : BigDecimal.ZERO);
        data.put("value", order.getTotalPrice());
        data.put("products", order.getDetails().stream().map(this::buildProductPayload).toList());
        return data;
    }

    private Map<String, Object> buildProductPayload(OrderDetail detail) {
        return Map.of(
                "name", detail.getProduct().getName(),
                "price", detail.getPrice(),
                "quantity", detail.getQuantity());
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
