package J2EE.com.example.project.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import J2EE.com.example.project.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "kh_id", nullable = false)
    private KhachHang khachHang;
    @Column(name = "order_date", nullable = false, length = 20)
    private String orderDate;
    @Column(name = "order_trangthai", nullable = false, length = 50)
    private String status;
    @Column(name = "order_adress", nullable = false, length = 250)
    private String address;
    @Column(name = "recipient_province", length = 100)
    private String recipientProvince;
    @Column(name = "recipient_district", length = 100)
    private String recipientDistrict;
    @Column(name = "recipient_ward", length = 100)
    private String recipientWard;
    @Column(name = "order_totalprice", nullable = false)
    @Builder.Default
    private Integer totalPrice = 0;
    @Column(name = "created_at", nullable = false)
    private java.time.Instant createdAt;
    @Column(name = "updated_at", nullable = false)
    private java.time.Instant updatedAt;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false, length = 20)
    @Builder.Default
    private PaymentMethod paymentMethod = PaymentMethod.VIETQR;

    @Version
    @Column(name = "version", nullable = false)
    @Builder.Default
    private Long version = 0L;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<OrderDetail> details = new ArrayList<>();

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();

    @PrePersist
    void prePersist() {
        java.time.Instant now = java.time.Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = java.time.Instant.now();
    }
}