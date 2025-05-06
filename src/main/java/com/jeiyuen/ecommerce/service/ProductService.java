package com.jeiyuen.ecommerce.service;

import com.jeiyuen.ecommerce.model.Product;
import com.jeiyuen.ecommerce.payload.ProductDTO;

public interface ProductService{

    ProductDTO addProduct(Long categoryId, Product product);
}
