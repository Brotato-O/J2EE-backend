package J2EE.com.example.project.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import J2EE.com.example.project.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Page<Category> findAllByStatus(Integer status, Pageable pageable);

    Optional<Category> findByIdAndStatus(Integer id, Integer status);
}