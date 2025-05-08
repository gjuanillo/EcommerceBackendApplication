package com.jeiyuen.ecommerce.service;

import java.io.IOException;

import com.jeiyuen.ecommerce.payload.ProductDTO;
import com.jeiyuen.ecommerce.payload.ProductResponse;

import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    // Add new product
    ProductDTO addProduct(Long categoryId, ProductDTO product);

    // List all products
    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    // List all products on a defined category
    ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    // List all products based on keyword
    ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    // Update product details
    ProductDTO updateProduct(Long productId, ProductDTO product);

    // Delete a product
    ProductDTO deleteProduct(Long productId);

    // Update product image
    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
