package com.example.spacecatsmarket.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntryId implements Serializable {

    @Column(name = "order_id", columnDefinition = "UUID")

    private Long orderId;

    @Column(name = "product_id", columnDefinition = "UUID")
    private Long productId;

}