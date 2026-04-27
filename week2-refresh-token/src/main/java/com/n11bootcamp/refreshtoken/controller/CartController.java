package com.n11bootcamp.refreshtoken.controller;

import com.n11bootcamp.refreshtoken.api.CartControllerContract;
import com.n11bootcamp.refreshtoken.dto.request.AddToCartRequest;
import com.n11bootcamp.refreshtoken.dto.request.UpdateCartItemRequest;
import com.n11bootcamp.refreshtoken.dto.response.CartResponse;
import com.n11bootcamp.refreshtoken.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController implements CartControllerContract {

    @Autowired
    private CartService cartService;

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Override
    public ResponseEntity<CartResponse> getCart() {
        return ResponseEntity.ok(cartService.getCart(getCurrentUsername()));
    }

    @Override
    public ResponseEntity<CartResponse> addToCart(AddToCartRequest request) {
        return ResponseEntity.ok(cartService.addToCart(getCurrentUsername(), request));
    }

    @Override
    public ResponseEntity<CartResponse> updateCartItem(Long cartItemId,
                                                       UpdateCartItemRequest request) {
        return ResponseEntity.ok(
                cartService.updateCartItem(getCurrentUsername(), cartItemId, request));
    }

    @Override
    public ResponseEntity<Void> removeFromCart(Long cartItemId) {
        cartService.removeFromCart(getCurrentUsername(), cartItemId);
        return ResponseEntity.ok().build();
    }
}