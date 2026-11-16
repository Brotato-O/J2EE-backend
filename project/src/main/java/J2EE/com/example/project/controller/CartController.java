
package J2EE.com.example.project.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import J2EE.com.example.project.dto.request.AddToCartRequest;
import J2EE.com.example.project.dto.request.UpdateCartItemRequest;
import J2EE.com.example.project.dto.response.CartResponse;
import J2EE.com.example.project.service.CartService;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@PreAuthorize("hasRole('User')")
public class CartController {

    private final CartService cartService;

    // User: xem giỏ hàng của chính mình
    @GetMapping
    public ResponseEntity<CartResponse> getMyCart() {
        return ResponseEntity.ok(cartService.getMyCart());
    }

    // User: thêm sản phẩm vào giỏ hàng
    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItem(
            @Valid @RequestBody AddToCartRequest request) {
        return ResponseEntity.ok(cartService.addItem(request));
    }

    // User: cập nhật sản phẩm trong giỏ hàng
    @PutMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> updateItem(
            @PathVariable Integer itemId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        return ResponseEntity.ok(
                cartService.updateItem(itemId, request));
    }

    // User: xóa một sản phẩm khỏi giỏ hàng
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<CartResponse> removeItem(
            @PathVariable Integer itemId) {
        return ResponseEntity.ok(cartService.removeItem(itemId));
    }

    // User: xóa toàn bộ giỏ hàng
    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }
}
