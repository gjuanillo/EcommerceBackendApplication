package com.jeiyuen.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import com.jeiyuen.ecommerce.model.Category;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController{
    private List<Category> categories = new ArrayList<>();


    //Display all categories
    @GetMapping("/api/public/categories")
    public List<Category> getAllCategories(){
        return categories;
    }
}
