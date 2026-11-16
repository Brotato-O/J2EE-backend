package J2EE.com.example.project.entity;

import java.time.Instant;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shipping_webhook_events", uniqueConstraints = @UniqueConstraint(
        name = "uk_shipping_webhook_event",
        columnNames = { "provider", "event_key" }))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingWebhookEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String provider;

    @Column(name = "event_key", nullable = false, length = 255)
    private String eventKey;

    @Column(name = "tracking_code", nullable = false, length = 100)
    private String trackingCode;

    @Column(name = "status_id", nullable = false)
    private Integer statusId;

    @Column(nullable = false, columnDefinition = "JSON")
    private String payload;

    @Column(nullable = false)
    @Builder.Default
    private boolean processed = false;

    @Column(name = "received_at", nullable = false)
    private Instant receivedAt;

    @Column(name = "processed_at")
    private Instant processedAt;

    @PrePersist
    void prePersist() {
        receivedAt = Instant.now();
    }
}
