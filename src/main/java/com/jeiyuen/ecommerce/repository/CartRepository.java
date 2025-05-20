package com.jeiyuen.ecommerce.repository;

import com.jeiyuen.ecommerce.model.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>{

}
