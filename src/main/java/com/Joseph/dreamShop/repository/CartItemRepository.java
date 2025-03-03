package com.Joseph.dreamShop.repository;

import com.Joseph.dreamShop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    static void deleteAllByCartId(Long id) {
    }
}
