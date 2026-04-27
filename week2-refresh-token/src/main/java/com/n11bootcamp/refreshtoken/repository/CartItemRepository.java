package com.n11bootcamp.refreshtoken.repository;

import com.n11bootcamp.refreshtoken.model.Cart;
import com.n11bootcamp.refreshtoken.model.CartItem;
import com.n11bootcamp.refreshtoken.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}