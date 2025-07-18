package com.jeiyuen.ecommerce.controller;

import com.jeiyuen.ecommerce.payload.OrderDTO;
import com.jeiyuen.ecommerce.payload.OrderRequestDTO;
import com.jeiyuen.ecommerce.service.OrderService;
import com.jeiyuen.ecommerce.utility.AuthUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {

    private OrderService orderService;
    private AuthUtil authUtil;

    @Autowired
    public OrderController(OrderService orderService, AuthUtil authUtil) {
        this.orderService = orderService;
        this.authUtil = authUtil;
    }

    @PostMapping("/order/users/payments/{pm}")
    public ResponseEntity<OrderDTO> orderProducts(@PathVariable("pm") String paymentMethod,
            @RequestBody OrderRequestDTO orderRequestDTO) {
        String emailId = authUtil.loggedInEmail();
        OrderDTO orderDTO = orderService.placeOrder(emailId, paymentMethod, orderRequestDTO);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }

}
