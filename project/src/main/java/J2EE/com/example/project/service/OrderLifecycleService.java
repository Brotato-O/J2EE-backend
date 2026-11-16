package J2EE.com.example.project.service;

import J2EE.com.example.project.enums.FulfillmentStatus;
import J2EE.com.example.project.entity.Order;

public interface OrderLifecycleService {
    Order transition(Integer orderId, FulfillmentStatus target, String reason);
    boolean tryTransition(Order order, FulfillmentStatus target, String reason);
}
