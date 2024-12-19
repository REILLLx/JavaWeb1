package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.DTO.product.ProductDTO;
import com.example.spacecatsmarket.DTO.product.ProductEntryDTO;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

public interface ProductService {
    ProductEntryDTO createProduct(ProductEntryDTO productEntryDTO);
    List<ProductEntryDTO> getAllProducts();
    ProductEntryDTO getProductById(Long id);
    ProductEntryDTO updateProduct(@Parameter Long id, @Parameter ProductEntryDTO productEntryDTO);
    void deleteProduct(Long id);
}