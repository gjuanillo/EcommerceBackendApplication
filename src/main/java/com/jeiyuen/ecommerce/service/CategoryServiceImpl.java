package com.jeiyuen.ecommerce.service;

import java.util.List;

import com.jeiyuen.ecommerce.exceptions.ApiException;
import com.jeiyuen.ecommerce.exceptions.ResourceNotFoundException;
import com.jeiyuen.ecommerce.model.Category;
import com.jeiyuen.ecommerce.payload.CategoryDTO;
import com.jeiyuen.ecommerce.payload.CategoryResponse;
import com.jeiyuen.ecommerce.repository.CategoryRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository theCategoryRepository, ModelMapper theModelMapper) {
        categoryRepository = theCategoryRepository;
        modelMapper = theModelMapper;
    }

    // Return list of categories
    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()){
            throw new ApiException("Category List is empty!");
        }
        List<CategoryDTO> categoryDTOs = categories.stream()
            .map(category -> modelMapper.map(category, CategoryDTO.class))
            .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOs);
        return categoryResponse;
    }

    // Save Category
    @Override
    public void createCategory(Category category) {
        if (category.getCategoryId() != null) {
            throw new ApiException("Category ID is automatically genereated, cannot assign ID!");
        }
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory != null){
            throw new ApiException("Category with the name " + category.getCategoryName() + " already exists!");
        }
        categoryRepository.save(category);
    }

    // Delete Category
    @Override
    public String deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", id)); 
        categoryRepository.delete(category);
        return "Category with categoryID: " + id + " deleted successfully!";
    }

    // Update Category
    @Override
    public Category updateCategory(Long id, Category category) {
        Category savedCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", id));
        category.setCategoryId(id);
        savedCategory = categoryRepository.save(category);
        return savedCategory;
    }
}
