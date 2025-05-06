package com.jeiyuen.ecommerce.service;

import com.jeiyuen.ecommerce.model.Product;
import com.jeiyuen.ecommerce.payload.ProductDTO;
import com.jeiyuen.ecommerce.payload.ProductResponse;

public interface ProductService {

    // Add new product
    ProductDTO addProduct(Long categoryId, Product product);

    // List all products
    ProductResponse getAllProducts();
}
