
package J2EE.com.example.project.controller;

import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import J2EE.com.example.project.dto.request.OrderRequest;
import J2EE.com.example.project.dto.request.UpdateOrderStatusRequest;
import J2EE.com.example.project.dto.response.OrderResponse;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // ===================== CUSTOMER ENDPOINTS =====================

    // Chỉ User được đặt hàng
    @PreAuthorize("hasRole('User')")
    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            @Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderService.placeOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Chỉ User xem đơn hàng của chính mình
    @PreAuthorize("hasRole('User')")
    @GetMapping("/my")
    public ResponseEntity<List<OrderResponse>> getMyOrders() {
        return ResponseEntity.ok(orderService.getMyOrders());
    }

    // Chỉ User xem chi tiết đơn hàng của chính mình
    @PreAuthorize("hasRole('User')")
    @GetMapping("/my/{id}")
    public ResponseEntity<OrderResponse> getMyOrderById(
            @PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getMyOrderById(id));
    }

    // Chỉ User hủy đơn hàng của chính mình
    @PreAuthorize("hasRole('User')")
    @PutMapping("/my/{id}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(
            @PathVariable Integer id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    // ===================== ADMIN / NHÂN VIÊN ENDPOINTS =====================

    // Admin, Nhân Viên xem tất cả đơn hàng
    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên')")
    @GetMapping
    public ResponseEntity<PageResponse<OrderResponse>> getAllOrders(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.getAllOrders(status, pageable));
    }

    // Admin, Nhân Viên cập nhật trạng thái đơn hàng
    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên', 'Nhân Viên Kho')")
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        return ResponseEntity.ok(orderService.updateStatus(id, request));
    }
}
