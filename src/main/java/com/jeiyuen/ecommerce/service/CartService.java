package com.jeiyuen.ecommerce.service;

import java.util.List;

import jakarta.transaction.Transactional;

import com.jeiyuen.ecommerce.payload.CartDTO;

public interface CartService {

    CartDTO addProductToCart(Long productId, Integer quantity);

    List<CartDTO> getAllCarts();

    CartDTO getCart(String emailId, Long cartId);

    @Transactional
    CartDTO updateProductQuantityInCart(Long productId, Integer i);
}
