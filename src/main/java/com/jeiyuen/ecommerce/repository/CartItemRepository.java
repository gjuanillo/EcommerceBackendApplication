package com.jeiyuen.ecommerce.repository;

import com.jeiyuen.ecommerce.model.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
