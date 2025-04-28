package com.jeiyuen.ecommerce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jeiyuen.ecommerce.model.Category;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CategoryServiceImpl implements CategoryService {

    // Save Categories to an Array temporarily
    private List<Category> categories = new ArrayList<>();
    Long incrementID = 1L;

    // Return list of categories
    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    // Save Category
    @Override
    public void createCategory(Category category) {
        if (category.getCategoryId() != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ID is automatically generated, cannot assign ID");
        }
        category.setCategoryId(incrementID++);
        categories.add(category);
    }

    // Delete Category
    @Override
    public String deleteCategory(Long id) {
        Category category = categories.stream()
                .filter(c -> c.getCategoryId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found!"));
        categories.remove(category);
        return "Category with categoryID: " + id + " deleted successfully!";
    }

    // Update Category
    @Override
    public Category updateCategory(Long id, Category category) {
        Optional<Category> optionalCategory = categories.stream()
                .filter(c -> c.getCategoryId().equals(id))
                .findFirst();
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            return existingCategory;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!");
        }
    }
}
