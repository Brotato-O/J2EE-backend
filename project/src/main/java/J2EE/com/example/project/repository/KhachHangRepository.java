package J2EE.com.example.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import J2EE.com.example.project.entity.KhachHang;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepository
        extends JpaRepository<KhachHang, Integer> {
    Optional<KhachHang> findByEmail(String email);

    boolean existsByEmail(String email);
}