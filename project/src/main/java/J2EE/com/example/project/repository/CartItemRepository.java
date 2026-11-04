package J2EE.com.example.project.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import J2EE.com.example.project.entity.CartItem;

public interface CartItemRepository
        extends JpaRepository<CartItem, Integer> {

    List<CartItem> findAllByCartId(Integer cartId);

    Optional<CartItem> findByIdAndCartId(
            Integer itemId,
            Integer cartId);

    Optional<CartItem> findByCartIdAndVariantId(
            Integer cartId,
            Integer variantId);

    void deleteAllByCartId(Integer cartId);
}