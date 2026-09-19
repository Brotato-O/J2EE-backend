package J2EE.com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import J2EE.com.example.project.entity.ThuongHieu;

public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, Integer> {
}