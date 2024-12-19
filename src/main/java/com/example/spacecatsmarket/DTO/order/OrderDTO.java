package com.example.spacecatsmarket.DTO.order;

import com.example.spacecatsmarket.domain.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;
import lombok.Value;


import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class OrderDTO {
    long id;
    LocalDateTime orderDate;
    String customerName;
    String email;
}
