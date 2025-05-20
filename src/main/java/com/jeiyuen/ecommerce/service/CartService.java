package com.jeiyuen.ecommerce.service;

import java.util.List;

import com.jeiyuen.ecommerce.payload.CartDTO;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCarts();
}
