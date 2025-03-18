package com.Joseph.dreamShop.repository;

import com.Joseph.dreamShop.model.Cart;
import com.Joseph.dreamShop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
