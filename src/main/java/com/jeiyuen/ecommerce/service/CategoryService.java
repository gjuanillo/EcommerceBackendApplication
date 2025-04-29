package com.jeiyuen.ecommerce.service;

import com.jeiyuen.ecommerce.model.Category;
import com.jeiyuen.ecommerce.payload.CategoryResponse;

public interface CategoryService{
    //Retrieve all categories
    CategoryResponse getAllCategories();
    //Save category
    void createCategory(Category category);
    //Delete category by id
    String deleteCategory(Long id);
    //Update category by id
    Category updateCategory(Long id, Category category);
}
