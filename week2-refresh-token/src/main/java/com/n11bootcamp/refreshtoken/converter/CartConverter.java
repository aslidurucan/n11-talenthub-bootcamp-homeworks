package com.n11bootcamp.refreshtoken.converter;

import com.n11bootcamp.refreshtoken.dto.response.CartItemResponse;
import com.n11bootcamp.refreshtoken.dto.response.CartResponse;
import com.n11bootcamp.refreshtoken.model.Cart;
import com.n11bootcamp.refreshtoken.model.CartItem;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartConverter {

    public CartItemResponse toItemResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();
        response.setId(cartItem.getId());
        response.setProductId(cartItem.getProduct().getId());
        response.setProductName(cartItem.getProduct().getName());
        response.setProductPrice(cartItem.getProduct().getPrice());
        response.setQuantity(cartItem.getQuantity());

        BigDecimal totalPrice = cartItem.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        response.setTotalPrice(totalPrice);

        return response;
    }

    public CartResponse toResponse(Cart cart) {
        CartResponse response = new CartResponse();
        response.setId(cart.getId());

        List<CartItemResponse> items = cart.getCartItems().stream()
                .map(this::toItemResponse)
                .collect(Collectors.toList());
        response.setItems(items);

        BigDecimal grandTotal = items.stream()
                .map(CartItemResponse::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        response.setGrandTotal(grandTotal);

        return response;
    }
}