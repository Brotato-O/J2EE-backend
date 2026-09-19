package J2EE.com.example.project.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import J2EE.com.example.project.entity.NhaCungCap;

public interface NhaCungCapRepository extends JpaRepository<NhaCungCap, Integer> {

    Page<NhaCungCap> findAllByStatus(Integer status, Pageable pageable);

    Optional<NhaCungCap> findByIdAndStatus(Integer id, Integer status);
}