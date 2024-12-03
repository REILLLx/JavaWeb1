package com.example.spacecatsmarket.DTO.product;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class ProductEntryDTO {
    long id;
    String name;
    double price;
    long categoryId;
    String description;
}

