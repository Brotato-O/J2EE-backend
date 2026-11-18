package J2EE.com.example.project.entity;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.*;
import J2EE.com.example.project.enums.ShipmentStatus;
import J2EE.com.example.project.enums.ShippingProviderType;

@Entity
@Table(name = "shipments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fulfillment_id", nullable = false)
    private Fulfillment fulfillment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private ShippingProviderType provider = ShippingProviderType.GHN;

    @Column(name = "tracking_code", length = 100)
    private String trackingCode;

    @Column(name = "provider_order_code", length = 100)
    private String providerOrderCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    @Builder.Default
    private ShipmentStatus status = ShipmentStatus.CREATED;

    @Version
    @Builder.Default
    private Long version = 0L;

    @Column(name = "last_error", length = 500)
    private String lastError;

    @Column(name = "stock_released", nullable = false)
    @Builder.Default
    private boolean stockReleased = false;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void prePersist() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void preUpdate() {
        updatedAt = Instant.now();
    }
}
