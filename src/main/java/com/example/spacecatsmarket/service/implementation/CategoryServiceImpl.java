package com.example.spacecatsmarket.service.implementation;

import com.example.spacecatsmarket.DTO.сategory.CategoryDTO;
import com.example.spacecatsmarket.mappers.CategoryMapper;
import com.example.spacecatsmarket.repository.CategoryRepository;
import com.example.spacecatsmarket.repository.entity.CategoryEntity;
import com.example.spacecatsmarket.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> categories = new ArrayList<>(categoryRepository.findAll());
        return categoryMapper.toCategoryDTOList(categories);
    }

    @Transactional(readOnly = true)
    public CategoryDTO getCategoryById(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return categoryMapper.toDTO(category);
    }

    @Transactional(propagation = Propagation.NESTED)
    public CategoryDTO createCategory(CategoryDTO CategoryDTO) {
        CategoryEntity category = categoryMapper.toEntity(CategoryDTO);
        CategoryEntity savedCategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedCategory);
    }

    @Transactional(propagation = Propagation.NESTED)
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        CategoryEntity category = categoryRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Category not found"))
                .toBuilder().name(categoryDTO.getName()).build();
        CategoryEntity savedcategory = categoryRepository.save(category);
        return categoryMapper.toDTO(savedcategory);
    }

    @Transactional
    public void deleteCategory(Long id) {
        try {
            if (categoryRepository.existsById(id)) {
                categoryRepository.deleteById(id);
            }
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException("Failed to delete category due to database error", ex);
        }
    }
}