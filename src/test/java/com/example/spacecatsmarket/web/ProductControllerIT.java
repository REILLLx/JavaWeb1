package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.DTO.ProductDTO;
import com.example.spacecatsmarket.service.implementation.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIT {

    @MockBean
    private ProductServiceImpl productServiceImpl;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateProduct() throws Exception {

        ProductDTO productDTO = ProductDTO.builder().name("Test Product Star").price(100.0).build();

        ProductDTO createdProduct = ProductDTO.builder().name("Test Product Comet").price(1523.0).build();

        when(productServiceImpl.createProduct(any(ProductDTO.class))).thenReturn(createdProduct);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(createdProduct)));

        verify(productServiceImpl, times(1)).createProduct(any(ProductDTO.class));
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<ProductDTO> productList = new ArrayList<>(); // Заповни даними продуктів

        when(productServiceImpl.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productList)));

        verify(productServiceImpl, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById() throws Exception {
        Long productId = 1L;
        ProductDTO product = ProductDTO.builder().build(); // Заповни даними продукту

        when(productServiceImpl.getProductById(anyLong())).thenReturn(product);

        mockMvc.perform(get("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(product)));

        verify(productServiceImpl, times(1)).getProductById(anyLong());
    }

    @Test
    void testUpdateProduct() throws Exception {
        Long productId = 1L;
        ProductDTO productDTO = ProductDTO.builder().name("Test Product Star").price(100.0).build();

        ProductDTO updatedProduct = ProductDTO.builder().name("Test Product Comet").price(1902.0).build();

        when(productServiceImpl.updateProduct(anyLong(), any(ProductDTO.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedProduct)));

        verify(productServiceImpl, times(1)).updateProduct(anyLong(), any(ProductDTO.class));
    }

    @Test
    void testDeleteProduct() throws Exception {
        Long productId = 1L;

        mockMvc.perform(delete("/api/v1/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(productServiceImpl, times(1)).deleteProduct(anyLong());
    }
}
