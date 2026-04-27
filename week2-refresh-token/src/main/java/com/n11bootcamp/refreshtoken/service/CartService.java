package com.n11bootcamp.refreshtoken.service;

import com.n11bootcamp.refreshtoken.dto.request.AddToCartRequest;
import com.n11bootcamp.refreshtoken.dto.request.UpdateCartItemRequest;
import com.n11bootcamp.refreshtoken.dto.response.CartResponse;

public interface CartService {

    CartResponse getCart(String username);

    CartResponse addToCart(String username, AddToCartRequest request);

    CartResponse updateCartItem(String username, Long cartItemId, UpdateCartItemRequest request);

    void removeFromCart(String username, Long cartItemId);
}