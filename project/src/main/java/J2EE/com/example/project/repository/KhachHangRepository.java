
package J2EE.com.example.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import J2EE.com.example.project.entity.KhachHang;

@Repository
public interface KhachHangRepository
        extends JpaRepository<KhachHang, Integer> {

    boolean existsByEmail(String email);

    Optional<KhachHang> findByEmail(String email);

    Optional<KhachHang> findByName(String name);
}
