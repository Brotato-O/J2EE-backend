package J2EE.com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import J2EE.com.example.project.entity.KhachHang;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepository
        extends JpaRepository<KhachHang, Integer> {

    boolean existsByEmail(String email);
}