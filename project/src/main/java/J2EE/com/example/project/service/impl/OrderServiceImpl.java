package J2EE.com.example.project.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
import J2EE.com.example.project.service.OrderLifecycleService;
import J2EE.com.example.project.exception.AppException;
import J2EE.com.example.project.exception.ErrorCode;
import J2EE.com.example.project.enums.FulfillmentStatus;
import J2EE.com.example.project.enums.PaymentMethod;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProChitietRepository proChitietRepository;
    private final KhachHangRepository khachHangRepository;
    private final OrderMapper orderMapper;
    private final OrderLifecycleService lifecycleService;

    // ===================== CUSTOMER OPERATIONS =====================
    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        KhachHang khachHang = getCurrentCustomer();
        // 1. Lấy giỏ hàng của khách hàng
        Cart cart = cartRepository.findByUserId(khachHang.getId())
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart.getId());
        if (cartItems.isEmpty()) {
            throw new AppException(ErrorCode.CART_EMPTY);
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
                .status(FulfillmentStatus.NEW.getDisplayName())
                .address(request.getAddress())
                .totalPrice(totalPrice)
                .paymentMethod(request.getPaymentMethod() == null
                        ? PaymentMethod.VIETQR : request.getPaymentMethod())
                .build();
        order = orderRepository.save(order);
        // 4. Tạo OrderDetail từ CartItem + trừ tồn kho
        for (CartItem cartItem : cartItems) {
            ProChitiet variant = cartItem.getVariant();
            // Kiểm tra tồn kho
            int currentStock = variant.getStockQuantity() == null ? 0 : variant.getStockQuantity();
            if (cartItem.getQuantity() > currentStock) {
                throw new AppException(ErrorCode.STOCK_EXCEEDED);
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
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
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
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        List<OrderDetail> details = orderDetailRepository.findByOrderId(order.getId());
        order.setDetails(details);
        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse cancelOrder(Integer orderId) {
        KhachHang khachHang = getCurrentCustomer();
        Order order = orderRepository.findByIdAndKhachHangId(orderId, khachHang.getId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        // Chỉ được hủy khi trạng thái là "Chờ xác nhận"
        if (!FulfillmentStatus.NEW.getDisplayName().equals(order.getStatus())) {
            throw new AppException(ErrorCode.INVALID_ORDER_TRANSITION);
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
        Order cancelledOrder = lifecycleService.transition(
                orderId,
                FulfillmentStatus.CANCELLED,
                "Customer cancelled order");
        cancelledOrder.setDetails(details);
        return orderMapper.toResponse(cancelledOrder);
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
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        String newStatus = request.getStatus().trim();
        FulfillmentStatus target = parseStatus(newStatus);
        // Nếu đang chuyển sang Đã hủy từ trạng thái != Chờ xác nhận → cộng lại tồn kho
        if (FulfillmentStatus.CANCELLED == parseStatus(newStatus)
                && FulfillmentStatus.CANCELLED != FulfillmentStatus.fromValue(order.getStatus())) {
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
        lifecycleService.transition(orderId, target, "Manual status update");
        order.setStatus(target.name());
        List<OrderDetail> details = orderDetailRepository.findByOrderId(order.getId());
        order.setDetails(details);
        return orderMapper.toResponse(order);
    }

    private FulfillmentStatus parseStatus(String status) {
        try {
            return FulfillmentStatus.fromValue(status);
        } catch (IllegalArgumentException exception) {
            throw new AppException(ErrorCode.INVALID_ORDER_STATUS);
        }
    }

    // ===================== PRIVATE HELPERS =====================
    private KhachHang getCurrentCustomer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()
                || "anonymousUser".equals(auth.getPrincipal())) {
            throw new AppException(ErrorCode.AUTHENTICATION_REQUIRED);
        }
        String email = auth.getName();
        return khachHangRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
    }
}