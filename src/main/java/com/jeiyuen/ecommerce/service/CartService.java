package com.jeiyuen.ecommerce.service;

import com.jeiyuen.ecommerce.payload.CartDTO;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);
}
