package com.example.spacecatsmarket.mappers;

import com.example.spacecatsmarket.DTO.сategory.CategoryDTO;
import com.example.spacecatsmarket.repository.entity.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDTO(CategoryEntity category);

    CategoryEntity toEntity(CategoryDTO categoryDTO);

    List<CategoryDTO> toCategoryDTOList(List<CategoryEntity> categoryList);

    List<CategoryEntity> toCategoryEntityList(List<CategoryDTO> categoryDTOList);
}
