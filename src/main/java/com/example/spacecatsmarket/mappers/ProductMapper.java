package com.example.spacecatsmarket.mappers;

import com.example.spacecatsmarket.DTO.ProductDTO;
import com.example.spacecatsmarket.domain.Product;
import org.mapstruct.Mapper;



import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO productDTO);

    List<ProductDTO> toProductDTOList(List<Product> productList);
    List<Product> toProductEntityList(List<ProductDTO> productDTOList);

}
