package J2EE.com.example.project.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import J2EE.com.example.project.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @EntityGraph(attributePaths = { "brand", "category", "supplier" })
    Page<Product> findAllByStatus(Integer status, Pageable pageable);

    @EntityGraph(attributePaths = { "brand", "category", "supplier", "variants", "variants.size", "variants.color" })
    Optional<Product> findByIdAndStatus(Integer id, Integer status);
}