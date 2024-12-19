package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.DTO.order.OrderDTO;
import com.example.spacecatsmarket.DatabaseConfig;
import com.example.spacecatsmarket.repository.CategoryRepository;
import com.example.spacecatsmarket.repository.OrderEntryRepository;
import com.example.spacecatsmarket.repository.OrderRepository;
import com.example.spacecatsmarket.repository.ProductRepository;
import com.example.spacecatsmarket.repository.entity.OrderEntity;
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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Testcontainers
class OrderControllerIT extends DatabaseConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderEntryRepository orderEntryRepository;

    @BeforeEach
    void setUp() {
        orderEntryRepository.deleteAll();
        orderRepository.deleteAll();
        productRepository.deleteAll();
        categoryRepository.deleteAll();
    }
    @Test
    void testCreateOrder() throws Exception {
        OrderDTO orderDTO = OrderDTO.builder()
                .customerName("Test Customer")
                .email("testcustomer@mail.com")
                .build();

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerName").value("Test Customer"))
                .andExpect(jsonPath("$.email").value("testcustomer@mail.com"));
    }
    @Test
    void testGetOrderById() throws Exception {
        OrderEntity order = orderRepository.save(
                OrderEntity.builder()
                        .customerName("Test Customer")
                        .orderDate(LocalDateTime.now())
                        .email("testcustomer@mail.com")
                        .build()
        );

        mockMvc.perform(get("/api/v1/orders/{orderId}", order.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Test Customer"))
                .andExpect(jsonPath("$.email").value("testcustomer@mail.com"));
    }
    @Test
    void testDeleteOrderById() throws Exception {
        OrderEntity order = orderRepository.save(
                OrderEntity.builder()
                        .customerName("Test Customer")
                        .orderDate(LocalDateTime.now())
                        .email("testcustomer@mail.com")
                        .build()
        );

        mockMvc.perform(delete("/api/v1/orders/{orderId}", order.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertFalse(orderRepository.existsById(order.getId()));
    }

}