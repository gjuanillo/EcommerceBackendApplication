package com.jeiyuen.ecommerce.service;

import com.jeiyuen.ecommerce.payload.CategoryDTO;
import com.jeiyuen.ecommerce.payload.CategoryResponse;

public interface CategoryService{
    //Retrieve all categories
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    //Save category
    CategoryDTO createCategory(CategoryDTO dto);
    //Delete category by id
    CategoryDTO deleteCategory(Long id);
    //Update category by id
    CategoryDTO updateCategory(Long id, CategoryDTO dto);
}
