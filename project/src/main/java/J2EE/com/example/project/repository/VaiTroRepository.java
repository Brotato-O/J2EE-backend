package J2EE.com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import J2EE.com.example.project.entity.VaiTro;

@Repository
public interface VaiTroRepository
                extends JpaRepository<VaiTro, Integer> {
}