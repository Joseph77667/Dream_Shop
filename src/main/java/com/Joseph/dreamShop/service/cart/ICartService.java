package com.Joseph.dreamShop.service.cart;

import com.Joseph.dreamShop.dto.CartDto;
import com.Joseph.dreamShop.model.Cart;
import com.Joseph.dreamShop.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializeNewCart();
    Cart getCartByUserId(Long userId);

    CartDto convertToCartDto(Cart cart);
}
