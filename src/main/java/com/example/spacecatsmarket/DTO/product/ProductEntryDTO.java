package com.example.spacecatsmarket.DTO.product;

import com.example.spacecatsmarket.DTO.validation.OnCreate;
import com.example.spacecatsmarket.DTO.validation.OnUpdate;
import com.example.spacecatsmarket.validation.CosmicWordCheck;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.math.BigDecimal;
@Data
@Value
@Builder(toBuilder = true)
public class ProductEntryDTO {
    long id;
    @NotNull(message = "Product name cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    @CosmicWordCheck(message = "Product name must contain a cosmic term like 'star', 'galaxy', or 'comet'")
    String name;
    @NotNull(message = "Price cannot be null", groups = {OnCreate.class, OnUpdate.class})
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    BigDecimal price;
    @NotNull(message = "Category ID cannot be null", groups = {OnCreate.class, OnUpdate.class})
    long categoryId;
    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    String description;
}

