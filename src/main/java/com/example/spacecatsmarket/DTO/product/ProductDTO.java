package com.example.spacecatsmarket.DTO.product;


import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
public class ProductDTO {
    String name;
    BigDecimal price;
    long categoryId;
    String description;
}