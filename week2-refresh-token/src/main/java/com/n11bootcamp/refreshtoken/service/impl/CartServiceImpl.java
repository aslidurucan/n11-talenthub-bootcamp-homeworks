package com.n11bootcamp.refreshtoken.service.impl;

import com.n11bootcamp.refreshtoken.converter.CartConverter;
import com.n11bootcamp.refreshtoken.dto.request.AddToCartRequest;
import com.n11bootcamp.refreshtoken.dto.request.UpdateCartItemRequest;
import com.n11bootcamp.refreshtoken.dto.response.CartResponse;
import com.n11bootcamp.refreshtoken.model.Cart;
import com.n11bootcamp.refreshtoken.model.CartItem;
import com.n11bootcamp.refreshtoken.model.Product;
import com.n11bootcamp.refreshtoken.model.User;
import com.n11bootcamp.refreshtoken.repository.CartItemRepository;
import com.n11bootcamp.refreshtoken.repository.CartRepository;
import com.n11bootcamp.refreshtoken.repository.ProductRepository;
import com.n11bootcamp.refreshtoken.repository.UserRepository;
import com.n11bootcamp.refreshtoken.exception.BusinessException;
import com.n11bootcamp.refreshtoken.exception.EntityNotFoundException;
import com.n11bootcamp.refreshtoken.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartConverter cartConverter;

    private User getUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Kullanıcı bulunamadı"));
    }

    private Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart(String username) {
        User user = getUser(username);
        Cart cart = getOrCreateCart(user);
        return cartConverter.toResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse addToCart(String username, AddToCartRequest request) {
        User user = getUser(username);
        Cart cart = getOrCreateCart(user);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Ürün bulunamadı"));

        if (product.getStock() < request.getQuantity()) {
            throw new BusinessException("Yetersiz stok");
        }

        cartItemRepository.findByCartAndProduct(cart, product)
                .ifPresentOrElse(
                        existingItem -> {
                            existingItem.setQuantity(
                                    existingItem.getQuantity() + request.getQuantity());
                            cartItemRepository.save(existingItem);
                        },
                        () -> {
                            CartItem newItem = new CartItem();
                            newItem.setCart(cart);
                            newItem.setProduct(product);
                            newItem.setQuantity(request.getQuantity());
                            cartItemRepository.save(newItem);
                        }
                );

        Cart updatedCart = cartRepository.findByUser(user)
                .orElseThrow();
        return cartConverter.toResponse(updatedCart);
    }

    @Override
    @Transactional
    public CartResponse updateCartItem(String username, Long cartItemId,
                                       UpdateCartItemRequest request) {
        User user = getUser(username);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Sepet ürünü bulunamadı"));

        if (!cartItem.getCart().getUser().getId().equals(user.getId())) {
            throw new BusinessException("Bu işlem için yetkiniz yok");
        }

        cartItem.setQuantity(request.getQuantity());
        cartItemRepository.save(cartItem);

        Cart cart = getOrCreateCart(user);
        return cartConverter.toResponse(cart);
    }

    @Override
    @Transactional
    public void removeFromCart(String username, Long cartItemId) {
        User user = getUser(username);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new EntityNotFoundException("Sepet ürünü bulunamadı"));

        if (!cartItem.getCart().getUser().getId().equals(user.getId())) {
            throw new org.springframework.security.access.AccessDeniedException("Bu işlem için yetkiniz yok");
        }

        cartItemRepository.delete(cartItem);
    }
}