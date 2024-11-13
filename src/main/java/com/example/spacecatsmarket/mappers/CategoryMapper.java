package com.example.spacecatsmarket.mappers;

import com.example.spacecatsmarket.DTO.CategoryDTO;
import com.example.spacecatsmarket.domain.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDTO(Category category);
    Category toEntity(CategoryDTO categoryDTO);
}
