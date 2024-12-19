package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.DTO.сategory.CategoryDTO;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO CategoryDTO);

    List<CategoryDTO> getAllCategories();

    CategoryDTO getCategoryById(Long id);

    CategoryDTO updateCategory(@Parameter Long id, @Parameter CategoryDTO CategoryDTO);

    void deleteCategory(Long id);
}