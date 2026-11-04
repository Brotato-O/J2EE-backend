
package J2EE.com.example.project.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import J2EE.com.example.project.dto.request.AddToCartRequest;
import J2EE.com.example.project.dto.request.UpdateCartItemRequest;
import J2EE.com.example.project.dto.response.CartItemResponse;
import J2EE.com.example.project.dto.response.CartResponse;
import J2EE.com.example.project.entity.Cart;
import J2EE.com.example.project.entity.CartItem;
import J2EE.com.example.project.entity.KhachHang;
import J2EE.com.example.project.entity.ProChitiet;
import J2EE.com.example.project.mapper.CartMapper;
import J2EE.com.example.project.repository.CartItemRepository;
import J2EE.com.example.project.repository.CartRepository;
import J2EE.com.example.project.repository.KhachHangRepository;
import J2EE.com.example.project.repository.ProChitietRepository;
import J2EE.com.example.project.service.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

        private final CartRepository cartRepository;
        private final CartItemRepository cartItemRepository;
        private final ProChitietRepository variantRepository;
        private final KhachHangRepository khachHangRepository;
        private final CartMapper cartMapper;

        @Override
        public CartResponse getMyCart() {
                Cart cart = getOrCreateCart();
                return toCartResponse(cart);
        }

        @Override
        public CartResponse addItem(AddToCartRequest request) {
                Cart cart = getOrCreateCart();

                ProChitiet variant = variantRepository
                                .findById(request.getVariantId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Không tìm thấy biến thể"));

                int requestedQuantity = request.getQuantity();

                CartItem item = cartItemRepository
                                .findByCartIdAndVariantId(cart.getId(), variant.getId())
                                .orElse(null);

                int newQuantity = requestedQuantity;

                if (item != null) {
                        newQuantity = item.getQuantity() + requestedQuantity;
                }

                validateStock(newQuantity, variant);

                // Giá chung được lấy từ Product, không lấy từ request.
                BigDecimal price = variant.getProduct().getPrice();

                if (price == null || price.signum() < 0) {
                        throw new IllegalStateException(
                                        "Giá sản phẩm không hợp lệ");
                }

                if (item == null) {
                        item = CartItem.builder()
                                        .cart(cart)
                                        .variant(variant)
                                        .quantity(newQuantity)
                                        .price(price)
                                        .totalPrice(
                                                        price.multiply(BigDecimal.valueOf(newQuantity)))
                                        .build();
                } else {
                        item.setQuantity(newQuantity);
                        item.setTotalPrice(
                                        item.getPrice().multiply(
                                                        BigDecimal.valueOf(newQuantity)));
                }

                cartItemRepository.save(item);

                return toCartResponse(cart);
        }

        @Override
        public CartResponse updateItem(
                        Integer itemId,
                        UpdateCartItemRequest request) {

                Cart cart = getOrCreateCart();

                CartItem item = cartItemRepository
                                .findByIdAndCartId(itemId, cart.getId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Không tìm thấy sản phẩm trong giỏ hàng"));

                validateStock(request.getQuantity(), item.getVariant());

                item.updateQuantity(request.getQuantity());
                cartItemRepository.save(item);

                return toCartResponse(cart);
        }

        @Override
        public CartResponse removeItem(Integer itemId) {
                Cart cart = getOrCreateCart();

                CartItem item = cartItemRepository
                                .findByIdAndCartId(itemId, cart.getId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Không tìm thấy sản phẩm trong giỏ hàng"));

                cartItemRepository.delete(item);

                return toCartResponse(cart);
        }

        @Override
        public void clearCart() {
                Cart cart = getOrCreateCart();
                cartItemRepository.deleteAllByCartId(cart.getId());
        }

        private void validateStock(int quantity, ProChitiet variant) {
                if (quantity < 1) {
                        throw new IllegalArgumentException(
                                        "Số lượng phải lớn hơn 0");
                }

                if (variant.getStockQuantity() == null
                                || quantity > variant.getStockQuantity()) {
                        throw new IllegalArgumentException(
                                        "Số lượng vượt quá tồn kho của biến thể");
                }
        }

        private Cart getOrCreateCart() {
                KhachHang khachHang = getCurrentCustomer();

                return cartRepository.findByUserId(khachHang.getId())
                                .orElseGet(() -> cartRepository.save(
                                                Cart.builder()
                                                                .user(khachHang)
                                                                .build()));
        }

        private KhachHang getCurrentCustomer() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if (authentication == null
                                || !authentication.isAuthenticated()
                                || "anonymousUser".equals(authentication.getPrincipal())) {
                        throw new IllegalStateException(
                                        "Bạn cần đăng nhập");
                }

                // Giả định authentication.getName() trả về email.
                String email = authentication.getName();

                return khachHangRepository.findByEmail(email)
                                .orElseThrow(() -> new IllegalStateException(
                                                "Không tìm thấy khách hàng"));
        }

        private CartResponse toCartResponse(Cart cart) {
                List<CartItem> items = cartItemRepository.findAllByCartId(cart.getId());

                List<CartItemResponse> responses = cartMapper.toResponseList(items);

                BigDecimal totalPrice = responses.stream()
                                .map(CartItemResponse::getTotalPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                int totalQuantity = responses.stream()
                                .mapToInt(CartItemResponse::getQuantity)
                                .sum();

                return CartResponse.builder()
                                .cartId(cart.getId())
                                .items(responses)
                                .totalQuantity(totalQuantity)
                                .totalPrice(totalPrice)
                                .build();
        }
}
