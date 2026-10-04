package com.ecommerce.product.service;

import com.ecommerce.product.dto.CategoryDto;
import com.ecommerce.product.exception.ResourceNotFoundException;
import com.ecommerce.product.mapper.CategoryMapper;
import com.ecommerce.product.model.Category;
import com.ecommerce.product.reporitory.CategoryRepositlry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepositlry categoryRepositlry;


    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = CategoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryRepositlry.save(category);
        return CategoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category existingCategory = categoryRepositlry.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        existingCategory.setName(categoryDto.getName());
        existingCategory.setDescription(categoryDto.getDescription());
        existingCategory.setParentId(categoryDto.getParentId());
        Category updatedCategory = categoryRepositlry.save(existingCategory);
        return CategoryMapper.toDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepositlry.deleteById(id);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        return categoryRepositlry.findById(id)
                .map(CategoryMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepositlry.findAll().stream()
                .map(CategoryMapper::toDto)
                .toList();
    }
    // Implement the methods from CategoryService interface here
}
