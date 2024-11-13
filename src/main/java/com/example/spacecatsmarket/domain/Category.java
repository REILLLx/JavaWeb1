package com.example.spacecatsmarket.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Category {
    long id;
    String name;
}
