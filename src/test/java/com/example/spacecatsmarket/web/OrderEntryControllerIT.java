package com.example.spacecatsmarket.web;
import com.example.spacecatsmarket.DTO.order.OrderEntryDTO;
import com.example.spacecatsmarket.DatabaseConfig;
import com.example.spacecatsmarket.repository.CategoryRepository;
import com.example.spacecatsmarket.repository.OrderEntryRepository;
import com.example.spacecatsmarket.repository.OrderRepository;
import com.example.spacecatsmarket.repository.ProductRepository;
import com.example.spacecatsmarket.repository.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
class OrderEntryControllerIT extends DatabaseConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderEntryRepository orderEntryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        orderEntryRepository.deleteAll();
        orderRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }
    @Test
    void testAddProductToOrder() throws Exception {
        CategoryEntity category = categoryRepository.save(
                CategoryEntity.builder()
                        .name("Test Category")
                        .build()
        );

        ProductEntity product = productRepository.save(
                ProductEntity.builder()
                        .name("Test Product")
                        .price(BigDecimal.valueOf(100.0))
                        .description("Test Description")
                        .category(category)
                        .build()
        );

        OrderEntity order = orderRepository.save(
                OrderEntity.builder()
                        .customerName("Test Customer")
                        .orderDate(LocalDateTime.now())
                        .email("testcustomer@mail.com")
                        .build()
        );

        OrderEntryDTO orderEntryDTO = OrderEntryDTO.builder()
                .productId(product.getId())
                .amount(3)
                .build();

        mockMvc.perform(post("/api/v1/orders/entry/{orderId}/products", order.getId())
                        .param("productId", product.getId().toString())
                        .param("amount", "3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderEntryDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(product.getId()))
                .andExpect(jsonPath("$.amount").value(3));
    }
    @Test
    void testRemoveProductFromOrder() throws Exception {
        CategoryEntity category = categoryRepository.save(
                CategoryEntity.builder()
                        .name("Test Category")
                        .build()
        );

        ProductEntity product = productRepository.save(
                ProductEntity.builder()
                        .name("Test Product")
                        .price(BigDecimal.valueOf(100.0))
                        .description("Test Description")
                        .category(category)
                        .build()
        );

        OrderEntity order = orderRepository.save(
                OrderEntity.builder()
                        .customerName("Test Customer")
                        .orderDate(LocalDateTime.now())
                        .email("testcustomer@mail.com")
                        .build()
        );

        OrderEntryId orderEntryId = new OrderEntryId(order.getId(), product.getId());

        OrderEntryEntity orderEntry = OrderEntryEntity.builder()
                .id(orderEntryId)
                .order(order)
                .product(product)
                .amount(3)
                .build();

        mockMvc.perform(delete("/api/v1/orders/entry/{orderId}/products/{productId}", order.getId(), product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertFalse(orderEntryRepository.existsById(orderEntry.getId()));
    }
    @Test
    void testGetOrderEntries() throws Exception {
        CategoryEntity category = categoryRepository.save(
                CategoryEntity.builder()
                        .name("Test Category")
                        .build()
        );

        ProductEntity product = productRepository.save(
                ProductEntity.builder()
                        .name("Test Product")
                        .price(BigDecimal.valueOf(100.0))
                        .description("Test Description")
                        .category(category)
                        .build()
        );

        OrderEntity order = orderRepository.save(
                OrderEntity.builder()
                        .customerName("Test Customer")
                        .orderDate(LocalDateTime.now())
                        .email("testcustomer@mail.com")
                        .build()
        );

        OrderEntryId orderEntryId = new OrderEntryId(order.getId(), product.getId());

        OrderEntryEntity orderEntry = OrderEntryEntity.builder()
                .id(orderEntryId)
                .order(order)
                .product(product)
                .amount(3)
                .build();
        orderEntryRepository.save(orderEntry);


        mockMvc.perform(get("/api/v1/orders/entry/{orderId}/products", order.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}