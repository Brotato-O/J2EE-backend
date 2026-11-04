package J2EE.com.example.project.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import J2EE.com.example.project.entity.ProChitiet;

@Repository
public interface ProChitietRepository extends JpaRepository<ProChitiet, Integer> {
    /**
     * Tìm variant theo product + color + size.
     * Dùng để cộng lại tồn kho khi hủy đơn hàng.
     */
    @Query("SELECT p FROM ProChitiet p WHERE p.product.id = :productId AND p.color.id = :colorId AND p.size.id = :sizeId")
    Optional<ProChitiet> findByProductIdAndColorIdAndSizeId(
            @Param("productId") Integer productId,
            @Param("colorId") Integer colorId,
            @Param("sizeId") Integer sizeId);
}
