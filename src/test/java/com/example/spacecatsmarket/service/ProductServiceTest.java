package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.DTO.product.ProductDTO;
import com.example.spacecatsmarket.DTO.product.ProductEntryDTO;
import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.mappers.ProductMapperImpl;
import com.example.spacecatsmarket.service.implementation.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private ProductMapperImpl productMapper; // Використовуємо реальну реалізацію
    private ProductServiceImpl productServiceImpl; // Тестуємо реальний сервіс

    private ProductDTO productDTO;
    private Product product;

    @BeforeEach
    void setUp() {

        productMapper = new ProductMapperImpl(); // Ініціалізуємо реальний маппер
        productServiceImpl = new ProductServiceImpl(productMapper); // Передаємо маппер у сервіс

        productDTO = ProductDTO.builder()
                .name("Test Product")
                .price(100.0)
                .description("Test Description")
                .categoryId(1L)
                .build();

        product = Product.builder()
                .id(1L)
                .name("Test Product")
                .price(100.0)
                .description("Test Description")
                .categoryId(1L)
                .build();
    }
    @Test
    void testCreateProduct() {
        ProductEntryDTO createdProduct = productServiceImpl.createProduct(productDTO);

        assertNotNull(createdProduct);
        assertEquals("Test Product", createdProduct.getName());
    }

    @Test
    void testGetAllProducts() {
        productServiceImpl.createProduct(productDTO);
        List<ProductDTO> products = productServiceImpl.getAllProducts();

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getName());
    }

    @Test
    void testGetProductById() {
        productServiceImpl.createProduct(productDTO); // Збереження продукту для подальшого пошуку

        ProductEntryDTO foundProduct = productServiceImpl.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals("Test Product", foundProduct.getName());
    }

    @Test
    void testUpdateProduct() {
        productServiceImpl.createProduct(productDTO); // Збереження продукту для подальшого оновлення

        productDTO = productDTO.toBuilder().name("Updated Product").price(150.0).build();

        ProductEntryDTO updatedProduct = productServiceImpl.updateProduct(1L, productDTO);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals(150.0, updatedProduct.getPrice());
    }

    @Test
    void testDeleteProduct() {
        productServiceImpl.createProduct(productDTO);

        assertDoesNotThrow(() -> productServiceImpl.deleteProduct(1L));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productServiceImpl.getProductById(1L));
        assertEquals("Product not found", exception.getMessage());
    }
}

