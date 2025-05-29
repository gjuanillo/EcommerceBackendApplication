package com.jeiyuen.ecommerce.repository;

import com.jeiyuen.ecommerce.model.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
