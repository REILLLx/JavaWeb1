package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.DTO.product.ProductDTO;
import com.example.spacecatsmarket.DTO.product.ProductEntryDTO;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

public interface ProductService {
    ProductEntryDTO createProduct(ProductDTO productDTO);
    List<ProductDTO> getAllProducts();
    ProductEntryDTO getProductById(Long id);
    ProductEntryDTO updateProduct(@Parameter Long id, @Parameter ProductDTO productDTO);
    void deleteProduct(Long id);
}