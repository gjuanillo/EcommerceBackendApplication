package com.jeiyuen.ecommerce.repository;

import com.jeiyuen.ecommerce.model.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
