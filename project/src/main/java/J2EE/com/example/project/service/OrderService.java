package J2EE.com.example.project.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import J2EE.com.example.project.dto.request.OrderRequest;
import J2EE.com.example.project.dto.request.UpdateOrderStatusRequest;
import J2EE.com.example.project.dto.response.OrderResponse;
import J2EE.com.example.project.dto.response.PageResponse;

public interface OrderService {
    /**
     * Khách hàng đặt hàng từ giỏ hàng hiện tại.
     * - Lấy tất cả CartItem trong giỏ hàng của user đang đăng nhập
     * - Tạo Order + OrderDetail
     * - Trừ tồn kho
     * - Xóa giỏ hàng
     */
    OrderResponse placeOrder(OrderRequest request);

    /**
     * Lấy danh sách đơn hàng của khách hàng đang đăng nhập.
     */
    List<OrderResponse> getMyOrders();

    /**
     * Xem chi tiết 1 đơn hàng (chỉ xem được đơn của chính mình).
     */
    OrderResponse getMyOrderById(Integer orderId);

    /**
     * Khách hàng hủy đơn (chỉ hủy được khi trạng thái = "Chờ xác nhận").
     * Tồn kho được cộng lại.
     */
    OrderResponse cancelOrder(Integer orderId);

    /**
     * Admin/NV: Lấy tất cả đơn hàng có phân trang, lọc theo trạng thái.
     */
    PageResponse<OrderResponse> getAllOrders(String status, Pageable pageable);

    /**
     * Admin/NV: Cập nhật trạng thái đơn hàng.
     */
    OrderResponse updateStatus(Integer orderId, UpdateOrderStatusRequest request);
}
