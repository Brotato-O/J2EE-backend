package J2EE.com.example.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import J2EE.com.example.project.entity.Payment;
import J2EE.com.example.project.enums.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByOrderIdOrderByCreatedAtDesc(Integer orderId);

    Optional<Payment> findFirstByOrderIdAndStatusOrderByCreatedAtDesc(
            Integer orderId, PaymentStatus status);

    Optional<Payment> findByTransactionReference(String transactionReference);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Payment p where p.transactionReference = :reference")
    Optional<Payment> findByTransactionReferenceForUpdate(@Param("reference") String reference);
}
