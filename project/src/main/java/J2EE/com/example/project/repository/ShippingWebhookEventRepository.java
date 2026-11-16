package J2EE.com.example.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import J2EE.com.example.project.entity.ShippingWebhookEvent;

public interface ShippingWebhookEventRepository extends JpaRepository<ShippingWebhookEvent, Long> {
    Optional<ShippingWebhookEvent> findByProviderAndEventKey(String provider, String eventKey);
}
