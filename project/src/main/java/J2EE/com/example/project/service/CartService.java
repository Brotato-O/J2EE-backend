package J2EE.com.example.project.service;

import J2EE.com.example.project.dto.request.AddToCartRequest;
import J2EE.com.example.project.dto.request.UpdateCartItemRequest;
import J2EE.com.example.project.dto.response.CartResponse;

public interface CartService {

    CartResponse getMyCart();

    CartResponse addItem(AddToCartRequest request);

    CartResponse updateItem(
            Integer itemId,
            UpdateCartItemRequest request);

    CartResponse removeItem(Integer itemId);

    void clearCart();
}