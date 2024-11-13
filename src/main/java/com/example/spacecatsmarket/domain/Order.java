package com.example.spacecatsmarket.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
@Value
@Builder(toBuilder = true)
public class Order {
    long id;
    List<Product> products;
    LocalDateTime orderDate;
    String customerName;
}
