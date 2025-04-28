package com.jeiyuen.ecommerce.service;

import java.util.List;
import java.util.Optional;

import com.jeiyuen.ecommerce.model.Category;
import com.jeiyuen.ecommerce.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CategoryServiceImpl implements CategoryService {

    // Save Categories to an Array temporarily
    // private List<Category> categories = new ArrayList<>();
    // Long incrementID = 1L;

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository theCategoryRepository){
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
        if (category.getCategoryId() != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "ID is automatically generated, cannot assign ID");
        }
        // category.setCategoryId(incrementID++);
        categoryRepository.save(category);
    }

    // Delete Category
    @Override
    public String deleteCategory(Long id) {
        List<Category> categories = categoryRepository.findAll();
        Category category = categories.stream()
                .filter(c -> c.getCategoryId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found!"));
        categoryRepository.delete(category);
        return "Category with categoryID: " + id + " deleted successfully!";
    }

    // Update Category
    @Override
    public Category updateCategory(Long id, Category category) {
        List<Category> categories = categoryRepository.findAll();
        Optional<Category> optionalCategory = categories.stream()
                .filter(c -> c.getCategoryId().equals(id))
                .findFirst();
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            Category savedCategory = categoryRepository.save(existingCategory);
            return savedCategory;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!");
        }
    }
}
