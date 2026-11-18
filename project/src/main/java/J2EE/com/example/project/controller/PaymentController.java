
package J2EE.com.example.project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.MultiValueMap;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.Map;

import J2EE.com.example.project.dto.response.PaymentQrResponse;
import J2EE.com.example.project.service.PaymentService;
import J2EE.com.example.project.enums.PaymentProviderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

        private final PaymentService paymentService;

        @PostMapping("/orders/{orderId}/vietqr")
        @PreAuthorize("hasRole('User')")
        public ResponseEntity<PaymentQrResponse> createQr(
                        @PathVariable Integer orderId) {
                return ResponseEntity.ok(
                                paymentService.createOrGetPayment(orderId));
        }

        @GetMapping("/orders/{orderId}/vietqr")
        @PreAuthorize("hasRole('User')")
        public ResponseEntity<PaymentQrResponse> getQr(
                        @PathVariable Integer orderId) {
                return ResponseEntity.ok(
                                paymentService.createOrGetPayment(orderId));
        }

        @PostMapping("/orders/{orderId}/vnpay")
        @PreAuthorize("hasRole('User')")
        public ResponseEntity<PaymentQrResponse> createVnpay(
                        @PathVariable Integer orderId) {
                log.info("Creating VNPay payment for orderId: {}", orderId);
                return ResponseEntity.ok(
                                paymentService.createGatewayPayment(
                                                orderId, PaymentProviderType.VNPAY));
        }

        @GetMapping("/webhooks/vnpay")
        public ResponseEntity<Map<String, String>> vnpayWebhook(
                        @RequestParam MultiValueMap<String, String> parameters) {
                return ResponseEntity.ok(
                                paymentService.handleVnpayIpn(parameters.toSingleValueMap()));
        }

}
