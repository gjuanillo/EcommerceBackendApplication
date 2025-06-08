package com.jeiyuen.ecommerce.repository;

import com.jeiyuen.ecommerce.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
