package com.jeiyuen.ecommerce.service;

import java.util.List;

import com.jeiyuen.ecommerce.model.Category;
import com.jeiyuen.ecommerce.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository theCategoryRepository) {
        categoryRepository = theCategoryRepository;
    }

    // Return list of categories
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Save Category
    @Override
    public void createCategory(Category category) {
        if (category.getCategoryId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ID is automatically generated, cannot assign ID");
        }
        categoryRepository.save(category);
    }

    // Delete Category
    @Override
    public String deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found!"));
        categoryRepository.delete(category);
        return "Category with categoryID: " + id + " deleted successfully!";
    }

    // Update Category
    @Override
    public Category updateCategory(Long id, Category category) {
        Category savedCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found!"));
        category.setCategoryId(id);
        savedCategory = categoryRepository.save(category);
        return savedCategory;
    }
}
