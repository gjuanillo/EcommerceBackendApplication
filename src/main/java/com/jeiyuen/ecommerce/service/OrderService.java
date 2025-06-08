package com.jeiyuen.ecommerce.service;

import jakarta.transaction.Transactional;

import com.jeiyuen.ecommerce.payload.OrderDTO;
import com.jeiyuen.ecommerce.payload.OrderRequestDTO;

public interface OrderService{

    @Transactional
    OrderDTO placeOrder(String emailId, String paymentMethod, OrderRequestDTO orderRequestDTO);

}
