package com.n11bootcamp.refreshtoken.api;

import com.n11bootcamp.refreshtoken.dto.request.AddToCartRequest;
import com.n11bootcamp.refreshtoken.dto.request.UpdateCartItemRequest;
import com.n11bootcamp.refreshtoken.dto.response.CartResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/cart")
public interface CartControllerContract {

    @GetMapping
    ResponseEntity<CartResponse> getCart();

    @PostMapping("/items")
    ResponseEntity<CartResponse> addToCart(@Valid @RequestBody AddToCartRequest request);

    @PutMapping("/items/{cartItemId}")
    ResponseEntity<CartResponse> updateCartItem(
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequest request);

    @DeleteMapping("/items/{cartItemId}")
    ResponseEntity<Void> removeFromCart(@PathVariable Long cartItemId);
}