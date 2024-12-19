package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.DTO.сategory.CategoryDTO;
import com.example.spacecatsmarket.DatabaseConfig;
import com.example.spacecatsmarket.repository.CategoryRepository;
import com.example.spacecatsmarket.repository.entity.CategoryEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
class CategoryControllerIT extends DatabaseConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();
    }
    @Test
    void testCreateCategory() throws Exception {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .name("Test Category")
                .build();

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Category"));
    }
    @Test
    void testGetAllCategories() throws Exception {
        categoryRepository.saveAll(List.of(
                CategoryEntity.builder().name("Category 1").build(),
                CategoryEntity.builder().name("Category 2").build()
        ));

        mockMvc.perform(get("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Category 1"))
                .andExpect(jsonPath("$[1].name").value("Category 2"));
    }
    @Test
    void testGetCategoryById() throws Exception {
        // Додати категорію у базу даних
        CategoryEntity category = categoryRepository.save(
                CategoryEntity.builder().name("Test Category").build()
        );

        mockMvc.perform(get("/api/v1/categories/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Category"));
    }
    @Test
    void testUpdateCategory() throws Exception {
        CategoryEntity category = categoryRepository.save(
                CategoryEntity.builder().name("Old Category").build()
        );

        CategoryDTO updatedCategoryDTO = CategoryDTO.builder()
                .name("Updated Category")
                .build();

        mockMvc.perform(put("/api/v1/categories/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCategoryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Category"));
    }
    @Test
    void testDeleteCategory() throws Exception {
        CategoryEntity category = categoryRepository.save(
                CategoryEntity.builder().name("Test Category").build()
        );

        mockMvc.perform(delete("/api/v1/categories/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertFalse(categoryRepository.existsById(category.getId()));
    }
}