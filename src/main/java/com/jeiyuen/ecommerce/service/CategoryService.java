package com.jeiyuen.ecommerce.service;

import java.util.List;

import com.jeiyuen.ecommerce.model.Category;

public interface CategoryService{
    //Retrieve all categories
    List<Category> getAllCategories();
    //Save category
    void createCategory(Category category);
    //Delete category by id
    String deleteCategory(Long id);
    //Update category by id
    Category updateCategory(Long id, Category category);
}
