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
        if (categories.isEmpty()) {
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
    public CategoryDTO createCategory(CategoryDTO dto) {
        if (dto.getCategoryId() != null) {
            throw new ApiException("Category ID is automatically genereated, cannot assign ID!");
        }
        Category category = modelMapper.map(dto, Category.class);
        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryFromDb != null) {
            throw new ApiException("Category with the name " + dto.getCategoryName() + " already exists!");
        }

        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    // Delete Category
    @Override
    public CategoryDTO deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", id));
        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    // Update Category
    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
        Category savedCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", id));
        Category category = modelMapper.map(dto, Category.class);
        category.setCategoryId(id);
        savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}
