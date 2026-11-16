package J2EE.com.example.project;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import J2EE.com.example.project.enums.FulfillmentStatus;
import J2EE.com.example.project.entity.Order;
import J2EE.com.example.project.entity.OrderStatusHistory;
import J2EE.com.example.project.repository.FulfillmentRepository;
import J2EE.com.example.project.repository.OrderRepository;
import J2EE.com.example.project.repository.OrderStatusHistoryRepository;
import J2EE.com.example.project.service.impl.OrderLifecycleServiceImpl;

class OrderLifecycleServiceImplTest {

    private OrderRepository orderRepository;
    private FulfillmentRepository fulfillmentRepository;
    private OrderStatusHistoryRepository historyRepository;
    private OrderLifecycleServiceImpl service;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        fulfillmentRepository = mock(FulfillmentRepository.class);
        historyRepository = mock(OrderStatusHistoryRepository.class);
        service = new OrderLifecycleServiceImpl(orderRepository, fulfillmentRepository, historyRepository);
    }

    @Test
    void transitionsNewToConfirmedAndStoresHistory() {
        Order order = Order.builder().id(1).status("NEW").build();
        when(orderRepository.findByIdForUpdate(1)).thenReturn(Optional.of(order));
        when(fulfillmentRepository.findByOrderId(1)).thenReturn(Optional.empty());

        service.transition(1, FulfillmentStatus.CONFIRMED, "payment");

        assertEquals("CONFIRMED", order.getStatus());
        verify(historyRepository).save(any(OrderStatusHistory.class));
        verify(fulfillmentRepository).save(any());
    }

    @Test
    void rejectsSkippingPacking() {
        Order order = Order.builder().id(1).status("NEW").build();
        when(orderRepository.findByIdForUpdate(1)).thenReturn(Optional.of(order));

        assertThrows(IllegalStateException.class,
                () -> service.transition(1, FulfillmentStatus.SHIPPING, "invalid"));
        verify(historyRepository, never()).save(any());
    }
}
