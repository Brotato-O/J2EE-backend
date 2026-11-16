package J2EE.com.example.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import J2EE.com.example.project.entity.OrderStatusHistory;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {
    List<OrderStatusHistory> findAllByOrderIdOrderByCreatedAtAsc(Integer orderId);
}
