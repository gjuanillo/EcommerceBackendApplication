package com.jeiyuen.ecommerce.repository;

import java.util.List;

import com.jeiyuen.ecommerce.model.Category;
import com.jeiyuen.ecommerce.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryOrderByPriceAsc(Category category);

}
