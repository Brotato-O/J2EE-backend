package J2EE.com.example.project.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_chitiet", uniqueConstraints = {
        @UniqueConstraint(name = "uk_cart_variant", columnNames = { "cart_id", "ctiet_pro_id" })
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_chitiet_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ctiet_pro_id", nullable = false)
    private ProChitiet variant;

    @Column(name = "soluong", nullable = false)
    private Integer quantity;

    // Giá được lưu tại thời điểm thêm vào giỏ hàng
    @Column(name = "price", nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "total_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;

    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;
        this.totalPrice = price.multiply(BigDecimal.valueOf(quantity));
    }
}