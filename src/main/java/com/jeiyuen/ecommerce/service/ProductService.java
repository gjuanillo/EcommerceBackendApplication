package com.jeiyuen.ecommerce.service;

import com.jeiyuen.ecommerce.model.Product;
import com.jeiyuen.ecommerce.payload.ProductDTO;
import com.jeiyuen.ecommerce.payload.ProductResponse;

public interface ProductService{

    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts();
}
