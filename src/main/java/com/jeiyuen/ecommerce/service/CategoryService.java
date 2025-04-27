package com.jeiyuen.ecommerce.service;

import java.util.List;

import com.jeiyuen.ecommerce.model.Category;

public interface CategoryService{
    List<Category> getAllCategories();
    void createCategory(Category category);
}
