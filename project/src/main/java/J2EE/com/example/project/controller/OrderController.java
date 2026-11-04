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

    /**
     * POST /api/v1/orders
     * Khách hàng đặt hàng từ giỏ hàng hiện tại.
     */
    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            @Valid @RequestBody OrderRequest request) {
        OrderResponse response = orderService.placeOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/v1/orders/my
     * Lấy danh sách tất cả đơn hàng của khách hàng đang đăng nhập.
     */
    @GetMapping("/my") // Sửa lại thành /my
    public ResponseEntity<List<OrderResponse>> getMyOrders() {
        return ResponseEntity.ok(orderService.getMyOrders());
    }

    /**
     * GET /api/v1/orders/my/{id}
     * Xem chi tiết 1 đơn hàng (chỉ xem được đơn của chính mình).
     */
    @GetMapping("/my/{id}") // Sửa lại thành /my/{id}
    public ResponseEntity<OrderResponse> getMyOrderById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getMyOrderById(id));
    }

    /**
     * PUT /api/v1/orders/my/{id}/cancel
     * Khách hàng hủy đơn hàng (chỉ hủy được khi trạng thái = "Chờ xác nhận").
     */
    @PutMapping("/my/{id}/cancel") // Sửa lại thành /my/{id}/cancel
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.cancelOrder(id));
    }

    // ===================== ADMIN / NHÂN VIÊN ENDPOINTS =====================

    /**
     * GET /api/v1/orders?status=...&page=0&size=10
     * Admin/NV: Lấy tất cả đơn hàng có phân trang, lọc theo trạng thái.
     */
    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên')")
    @GetMapping
    public ResponseEntity<PageResponse<OrderResponse>> getAllOrders(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.getAllOrders(status, pageable));
    }

    /**
     * PUT /api/v1/orders/{id}/status
     * Admin/NV: Cập nhật trạng thái đơn hàng.
     */
    @PreAuthorize("hasAnyRole('Admin', 'Nhân Viên')")
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        return ResponseEntity.ok(orderService.updateStatus(id, request));
    }
}