package J2EE.com.example.project.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import J2EE.com.example.project.dto.request.OrderRequest;
import J2EE.com.example.project.dto.request.UpdateOrderStatusRequest;
import J2EE.com.example.project.dto.response.OrderResponse;
import J2EE.com.example.project.dto.response.PageResponse;
import J2EE.com.example.project.entity.Cart;
import J2EE.com.example.project.entity.CartItem;
import J2EE.com.example.project.entity.KhachHang;
import J2EE.com.example.project.entity.Order;
import J2EE.com.example.project.entity.OrderDetail;
import J2EE.com.example.project.entity.ProChitiet;
import J2EE.com.example.project.mapper.OrderMapper;
import J2EE.com.example.project.repository.CartItemRepository;
import J2EE.com.example.project.repository.CartRepository;
import J2EE.com.example.project.repository.KhachHangRepository;
import J2EE.com.example.project.repository.OrderDetailRepository;
import J2EE.com.example.project.repository.OrderRepository;
import J2EE.com.example.project.repository.ProChitietRepository;
import J2EE.com.example.project.service.OrderService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    // Trạng thái hợp lệ của đơn hàng
    private static final String STATUS_PENDING = "Chờ xác nhận";
    private static final String STATUS_CONFIRMED = "Đã xác nhận";
    private static final String STATUS_SHIPPING = "Đang giao";
    private static final String STATUS_DELIVERED = "Đã giao hàng";
    private static final String STATUS_CANCELLED = "Đã hủy";
    private static final Set<String> VALID_STATUSES = Set.of(
            STATUS_PENDING, STATUS_CONFIRMED, STATUS_SHIPPING, STATUS_DELIVERED, STATUS_CANCELLED);
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProChitietRepository proChitietRepository;
    private final KhachHangRepository khachHangRepository;
    private final OrderMapper orderMapper;

    // ===================== CUSTOMER OPERATIONS =====================
    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        KhachHang khachHang = getCurrentCustomer();
        // 1. Lấy giỏ hàng của khách hàng
        Cart cart = cartRepository.findByUserId(khachHang.getId())
                .orElseThrow(() -> new IllegalStateException("Giỏ hàng không tồn tại"));
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart.getId());
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Giỏ hàng đang trống, không thể đặt hàng");
        }
        // 2. Tính tổng tiền
        int totalPrice = cartItems.stream()
                .mapToInt(item -> item.getTotalPrice().intValue())
                .sum();
        // 3. Tạo Order
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy"));
        Order order = Order.builder()
                .khachHang(khachHang)
                .orderDate(today)
                .status(STATUS_PENDING)
                .address(request.getAddress())
                .totalPrice(totalPrice)
                .build();
        order = orderRepository.save(order);
        // 4. Tạo OrderDetail từ CartItem + trừ tồn kho
        for (CartItem cartItem : cartItems) {
            ProChitiet variant = cartItem.getVariant();
            // Kiểm tra tồn kho
            int currentStock = variant.getStockQuantity() == null ? 0 : variant.getStockQuantity();
            if (cartItem.getQuantity() > currentStock) {
                throw new IllegalStateException(
                        "Sản phẩm '" + variant.getProduct().getName()
                                + "' không đủ số lượng trong kho (còn " + currentStock + ")");
            }
            // Tạo OrderDetail
            OrderDetail detail = OrderDetail.builder()
                    .order(order)
                    .product(variant.getProduct())
                    .color(variant.getColor())
                    .size(variant.getSize())
                    .price(cartItem.getPrice().intValue())
                    .quantity(cartItem.getQuantity())
                    .totalPrice(cartItem.getTotalPrice().intValue())
                    .build();
            orderDetailRepository.save(detail);
            // Trừ tồn kho
            variant.setStockQuantity(currentStock - cartItem.getQuantity());
            proChitietRepository.save(variant);
        }
        // 5. Xóa toàn bộ giỏ hàng
        cartItemRepository.deleteAllByCartId(cart.getId());
        // 6. Load lại order để lấy details đã lưu
        Order savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(() -> new IllegalStateException("Lỗi hệ thống: không tìm thấy đơn hàng vừa tạo"));
        List<OrderDetail> details = orderDetailRepository.findByOrderId(savedOrder.getId());
        savedOrder.setDetails(details);
        return orderMapper.toResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getMyOrders() {
        KhachHang khachHang = getCurrentCustomer();
        List<Order> orders = orderRepository.findByKhachHangId(khachHang.getId());
        // Load details cho mỗi order
        orders.forEach(order -> {
            List<OrderDetail> details = orderDetailRepository.findByOrderId(order.getId());
            order.setDetails(details);
        });
        return orderMapper.toResponseList(orders);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getMyOrderById(Integer orderId) {
        KhachHang khachHang = getCurrentCustomer();
        Order order = orderRepository.findByIdAndKhachHangId(orderId, khachHang.getId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn hàng #" + orderId));
        List<OrderDetail> details = orderDetailRepository.findByOrderId(order.getId());
        order.setDetails(details);
        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse cancelOrder(Integer orderId) {
        KhachHang khachHang = getCurrentCustomer();
        Order order = orderRepository.findByIdAndKhachHangId(orderId, khachHang.getId())
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn hàng #" + orderId));
        // Chỉ được hủy khi trạng thái là "Chờ xác nhận"
        if (!STATUS_PENDING.equals(order.getStatus())) {
            throw new IllegalStateException(
                    "Không thể hủy đơn hàng ở trạng thái '" + order.getStatus() + "'");
        }
        // Cộng lại tồn kho
        List<OrderDetail> details = orderDetailRepository.findByOrderId(order.getId());
        for (OrderDetail detail : details) {
            // Tìm pro_chitiet theo product + color + size
            proChitietRepository
                    .findByProductIdAndColorIdAndSizeId(
                            detail.getProduct().getId(),
                            detail.getColor().getId(),
                            detail.getSize().getId())
                    .ifPresent(variant -> {
                        int newStock = (variant.getStockQuantity() == null ? 0 : variant.getStockQuantity())
                                + detail.getQuantity();
                        variant.setStockQuantity(newStock);
                        proChitietRepository.save(variant);
                    });
        }
        order.setStatus(STATUS_CANCELLED);
        order.setDetails(details);
        orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    // ===================== ADMIN OPERATIONS =====================
    @Override
    @Transactional(readOnly = true)
    public PageResponse<OrderResponse> getAllOrders(String status, Pageable pageable) {
        Page<Order> page;
        if (status == null || status.isBlank()) {
            page = orderRepository.findAllOrdersPageable(pageable);
        } else {
            page = orderRepository.findByStatusPageable(status, pageable);
        }
        List<OrderResponse> content = page.getContent().stream()
                .peek(order -> {
                    List<OrderDetail> details = orderDetailRepository.findByOrderId(order.getId());
                    order.setDetails(details);
                })
                .map(orderMapper::toResponse)
                .toList();
        return PageResponse.<OrderResponse>builder()
                .content(content)
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    @Override
    public OrderResponse updateStatus(Integer orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn hàng #" + orderId));
        String newStatus = request.getStatus().trim();
        if (!VALID_STATUSES.contains(newStatus)) {
            throw new IllegalArgumentException(
                    "Trạng thái '" + newStatus + "' không hợp lệ. "
                            + "Các trạng thái hợp lệ: " + VALID_STATUSES);
        }
        // Nếu đang chuyển sang Đã hủy từ trạng thái != Chờ xác nhận → cộng lại tồn kho
        if (STATUS_CANCELLED.equals(newStatus) && !STATUS_CANCELLED.equals(order.getStatus())) {
            List<OrderDetail> details = orderDetailRepository.findByOrderId(order.getId());
            for (OrderDetail detail : details) {
                proChitietRepository
                        .findByProductIdAndColorIdAndSizeId(
                                detail.getProduct().getId(),
                                detail.getColor().getId(),
                                detail.getSize().getId())
                        .ifPresent(variant -> {
                            int newStock = (variant.getStockQuantity() == null ? 0 : variant.getStockQuantity())
                                    + detail.getQuantity();
                            variant.setStockQuantity(newStock);
                            proChitietRepository.save(variant);
                        });
            }
        }
        order.setStatus(newStatus);
        orderRepository.save(order);
        List<OrderDetail> details = orderDetailRepository.findByOrderId(order.getId());
        order.setDetails(details);
        return orderMapper.toResponse(order);
    }

    // ===================== PRIVATE HELPERS =====================
    private KhachHang getCurrentCustomer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()
                || "anonymousUser".equals(auth.getPrincipal())) {
            throw new IllegalStateException("Bạn cần đăng nhập");
        }
        String email = auth.getName();
        return khachHangRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy khách hàng"));
    }
}