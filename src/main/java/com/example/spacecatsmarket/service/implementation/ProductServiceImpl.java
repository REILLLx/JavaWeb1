package com.example.spacecatsmarket.service.implementation;

import com.example.spacecatsmarket.DTO.product.ProductEntryDTO;
import com.example.spacecatsmarket.mappers.ProductMapper;
import com.example.spacecatsmarket.repository.CategoryRepository;
import com.example.spacecatsmarket.repository.ProductRepository;
import com.example.spacecatsmarket.repository.entity.CategoryEntity;
import com.example.spacecatsmarket.repository.entity.ProductEntity;
import com.example.spacecatsmarket.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper,
                              ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<ProductEntryDTO> getAllProducts() {
        List<ProductEntity> products = new ArrayList<>(productRepository.findAll());
        return productMapper.toProductDTOList(products);
    }

    @Transactional(propagation = Propagation.NESTED)
    public ProductEntryDTO createProduct(ProductEntryDTO productDTO) {
        CategoryEntity category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with ID: " + productDTO.getCategoryId()));

        ProductEntity product = ProductEntity.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .description(productDTO.getDescription())
                .category(category)
                .build();

        ProductEntity savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Transactional(propagation = Propagation.NESTED)
    public ProductEntryDTO updateProduct(Long id, ProductEntryDTO productDTO) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CategoryEntity category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setCategory(category);

        System.out.println("Product updated: " + product);

        ProductEntity savedProduct = productRepository.save(product);

        return productMapper.toDTO(savedProduct);
    }

    @Transactional(readOnly = true)
    public ProductEntryDTO getProductById(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return productMapper.toDTO(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        try {
            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
            }
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException("Failed to delete category due to database error", ex);
        }
    }


}