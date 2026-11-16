package J2EE.com.example.project.service.impl;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import J2EE.com.example.project.entity.Fulfillment;
import J2EE.com.example.project.enums.FulfillmentStatus;
import J2EE.com.example.project.entity.Order;
import J2EE.com.example.project.entity.OrderStatusHistory;
import J2EE.com.example.project.repository.FulfillmentRepository;
import J2EE.com.example.project.repository.OrderRepository;
import J2EE.com.example.project.repository.OrderStatusHistoryRepository;
import J2EE.com.example.project.service.OrderLifecycleService;
import J2EE.com.example.project.exception.AppException;
import J2EE.com.example.project.exception.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderLifecycleServiceImpl implements OrderLifecycleService {

    private final OrderRepository orderRepository;
    private final FulfillmentRepository fulfillmentRepository;
    private final OrderStatusHistoryRepository historyRepository;

    private final Map<FulfillmentStatus, EnumSet<FulfillmentStatus>> transitions = new EnumMap<>(Map.of(
            FulfillmentStatus.NEW, EnumSet.of(FulfillmentStatus.CONFIRMED, FulfillmentStatus.CANCELLED),
            FulfillmentStatus.CONFIRMED, EnumSet.of(FulfillmentStatus.PACKING, FulfillmentStatus.CANCELLED),
            FulfillmentStatus.PACKING, EnumSet.of(FulfillmentStatus.SHIPPING, FulfillmentStatus.CANCELLED),
            FulfillmentStatus.SHIPPING, EnumSet.of(FulfillmentStatus.DELIVERED,
                    FulfillmentStatus.DELIVERY_FAILED),
            FulfillmentStatus.DELIVERY_FAILED, EnumSet.of(FulfillmentStatus.RETURNED),
            FulfillmentStatus.DELIVERED, EnumSet.noneOf(FulfillmentStatus.class),
            FulfillmentStatus.RETURNED, EnumSet.noneOf(FulfillmentStatus.class),
            FulfillmentStatus.CANCELLED, EnumSet.noneOf(FulfillmentStatus.class)));

    @Override
    @Transactional
    public Order transition(Integer orderId, FulfillmentStatus target, String reason) {
        Order order = orderRepository.findByIdForUpdate(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        if (!tryTransition(order, target, reason)) {
            throw new IllegalStateException(ErrorCode.INVALID_ORDER_TRANSITION.getMessage());
        }
        return order;
    }

    @Override
    public boolean tryTransition(Order order, FulfillmentStatus target, String reason) {
        FulfillmentStatus current = normalize(order.getStatus());
        if (current == target) {
            return true;
        }
        if (!transitions.get(current).contains(target)) {
            return false;
        }

        historyRepository.save(OrderStatusHistory.builder()
                .order(order)
                .fromStatus(current.name())
                .toStatus(target.name())
                .reason(reason)
                .changedBy(currentActor())
                .build());
        order.setStatus(target.name());
        orderRepository.save(order);

        Fulfillment fulfillment = fulfillmentRepository.findByOrderId(order.getId())
                .orElseGet(() -> Fulfillment.builder()
                        .order(order)
                        .status(current)
                        .build());
        fulfillment.setStatus(target);
        fulfillmentRepository.save(fulfillment);
        return true;
    }

    private FulfillmentStatus normalize(String status) {
        return FulfillmentStatus.fromValue(status);
    }

    private String currentActor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {
            return "SYSTEM";
        }
        return authentication.getName();
    }
}
