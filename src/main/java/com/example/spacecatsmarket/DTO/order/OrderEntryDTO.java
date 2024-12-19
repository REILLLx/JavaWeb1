package com.example.spacecatsmarket.DTO.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderEntryDTO {
    private Long orderId;
    private Long productId;
    private int amount;
}
