package com.jeiyuen.ecommerce.service;

import com.jeiyuen.ecommerce.payload.ProductDTO;
import com.jeiyuen.ecommerce.payload.ProductResponse;

public interface ProductService {

    // Add new product
    ProductDTO addProduct(Long categoryId, ProductDTO product);

    // List all products
    ProductResponse getAllProducts();

    // List all products on a defined category
    ProductResponse searchByCategory(Long categoryId);

    // List all products based on keyword
    ProductResponse searchProductByKeyword(String keyword);

    // Update product details
    ProductDTO updateProduct(Long productId, ProductDTO product);

    // delete a product
    ProductDTO deleteProduct(Long productId);
}
