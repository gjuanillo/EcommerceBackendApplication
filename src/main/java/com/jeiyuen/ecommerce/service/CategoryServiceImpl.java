package com.jeiyuen.ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import com.jeiyuen.ecommerce.model.Category;

import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService{

    private List<Category> categories = new ArrayList<>();

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
       categories.add(category);
    }


}
