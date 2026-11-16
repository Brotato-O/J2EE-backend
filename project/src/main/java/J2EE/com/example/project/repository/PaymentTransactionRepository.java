package J2EE.com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import J2EE.com.example.project.entity.PaymentTransaction;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    boolean existsByProviderAndProviderTransactionId(
            J2EE.com.example.project.enums.PaymentProviderType provider,
            String providerTransactionId);
}
