package com.jeiyuen.ecommerce.controller;

import com.jeiyuen.ecommerce.model.Product;
import com.jeiyuen.ecommerce.payload.ProductDTO;
import com.jeiyuen.ecommerce.payload.ProductResponse;
import com.jeiyuen.ecommerce.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController{

    ProductService productService;

    public ProductController (ProductService productService){
        this.productService = productService;
    }

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product, @PathVariable("categoryId") Long categoryId){
        
        ProductDTO productDTO = productService.addProduct(categoryId, product);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }
    
    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(){
       ProductResponse productResponse = productService.getAllProducts(); 
       return new ResponseEntity<>(productResponse, HttpStatus.OK);

    }
}
