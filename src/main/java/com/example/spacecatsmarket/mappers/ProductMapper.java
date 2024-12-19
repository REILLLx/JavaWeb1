package com.example.spacecatsmarket.mappers;

import com.example.spacecatsmarket.DTO.product.ProductDTO;
import com.example.spacecatsmarket.DTO.product.ProductEntryDTO;
import com.example.spacecatsmarket.domain.Product;
import com.example.spacecatsmarket.repository.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId") // Мапимо ID категорії на categoryId
    ProductEntryDTO toDTO(ProductEntity product);

    @Mapping(source = "categoryId", target = "category.id") // Мапимо categoryId на об'єкт CategoryEntity
    ProductEntity toEntity(ProductEntryDTO productEntryDTO);

    List<ProductEntryDTO> toProductDTOList(List<ProductEntity> productList);
    List<ProductEntity> toProductEntityList(List<ProductEntryDTO> productDTOList);

}
