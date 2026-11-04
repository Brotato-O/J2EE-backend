package J2EE.com.example.project.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import J2EE.com.example.project.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Lấy tất cả đơn hàng của 1 khách hàng (theo kh_id), sắp xếp mới nhất trước
    @Query("SELECT o FROM Order o WHERE o.khachHang.id = :khId ORDER BY o.id DESC")
    List<Order> findByKhachHangId(@Param("khId") Integer khId);

    // Lấy 1 đơn hàng của 1 khách hàng cụ thể (kiểm tra ownership)
    @Query("SELECT o FROM Order o WHERE o.id = :orderId AND o.khachHang.id = :khId")
    Optional<Order> findByIdAndKhachHangId(@Param("orderId") Integer orderId, @Param("khId") Integer khId);

    // Admin: lấy tất cả đơn hàng có phân trang
    @Query("SELECT o FROM Order o ORDER BY o.id DESC")
    Page<Order> findAllOrdersPageable(Pageable pageable);

    // Admin: lọc theo trạng thái
    @Query("SELECT o FROM Order o WHERE (:status IS NULL OR o.status = :status) ORDER BY o.id DESC")
    Page<Order> findByStatusPageable(@Param("status") String status, Pageable pageable);
}
