package J2EE.com.example.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import J2EE.com.example.project.entity.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    Optional<Shipment> findByTrackingCode(String trackingCode);

    Optional<Shipment> findByFulfillmentId(Long fulfillmentId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Shipment s where s.fulfillment.id = :fulfillmentId")
    Optional<Shipment> findByFulfillmentIdForUpdate(@Param("fulfillmentId") Long fulfillmentId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Shipment s where s.trackingCode = :trackingCode")
    Optional<Shipment> findByTrackingCodeForUpdate(@Param("trackingCode") String trackingCode);
}
