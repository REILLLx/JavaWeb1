package com.example.spacecatsmarket.web;

import com.example.spacecatsmarket.DTO.product.ProductEntryDTO;
import com.example.spacecatsmarket.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<ProductEntryDTO> createProduct(@Valid @RequestBody ProductEntryDTO productDTO) {
        ProductEntryDTO createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping()
    public ResponseEntity<List<ProductEntryDTO>> getAllProducts() {
        List<ProductEntryDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntryDTO> getProductById(@PathVariable("id") Long id) {
        ProductEntryDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntryDTO> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody ProductEntryDTO productDTO) {
        ProductEntryDTO updatedProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}