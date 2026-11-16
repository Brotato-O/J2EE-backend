package J2EE.com.example.project.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import J2EE.com.example.project.dto.request.PaymentNotificationRequest;
import J2EE.com.example.project.dto.response.PaymentQrResponse;
import J2EE.com.example.project.service.PaymentService;
import J2EE.com.example.project.enums.PaymentProviderType;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/orders/{orderId}/vietqr")
    public ResponseEntity<PaymentQrResponse> createQr(@PathVariable Integer orderId) {
        return ResponseEntity.ok(paymentService.createOrGetPayment(orderId));
    }

    @GetMapping("/orders/{orderId}/vietqr")
    public ResponseEntity<PaymentQrResponse> getQr(@PathVariable Integer orderId) {
        return ResponseEntity.ok(paymentService.createOrGetPayment(orderId));
    }

    @PostMapping("/orders/{orderId}/vnpay")
    public ResponseEntity<PaymentQrResponse> createVnpay(@PathVariable Integer orderId) {
        return ResponseEntity.ok(paymentService.createGatewayPayment(orderId, PaymentProviderType.VNPAY));
    }

    @PostMapping("/orders/{orderId}/momo")
    public ResponseEntity<PaymentQrResponse> createMomo(@PathVariable Integer orderId) {
        return ResponseEntity.ok(paymentService.createGatewayPayment(orderId, PaymentProviderType.MOMO));
    }

    @PostMapping("/webhooks/vietqr")
    public ResponseEntity<Void> vietQrWebhook(
            @Valid @RequestBody PaymentNotificationRequest request) {
        paymentService.reconcile(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/webhooks/vnpay")
    public ResponseEntity<Void> vnpayWebhook(
            @Valid @RequestBody PaymentNotificationRequest request) {
        paymentService.reconcileGateway(PaymentProviderType.VNPAY, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/webhooks/momo")
    public ResponseEntity<Void> momoWebhook(
            @Valid @RequestBody PaymentNotificationRequest request) {
        paymentService.reconcileGateway(PaymentProviderType.MOMO, request);
        return ResponseEntity.ok().build();
    }
}
