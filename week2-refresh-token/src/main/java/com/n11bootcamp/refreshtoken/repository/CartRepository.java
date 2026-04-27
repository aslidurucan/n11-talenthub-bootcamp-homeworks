package com.n11bootcamp.refreshtoken.repository;

import com.n11bootcamp.refreshtoken.model.Cart;
import com.n11bootcamp.refreshtoken.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);
}