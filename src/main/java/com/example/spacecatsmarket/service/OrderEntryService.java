package com.example.spacecatsmarket.service;

import com.example.spacecatsmarket.DTO.order.OrderEntryDTO;

import java.util.List;

public interface OrderEntryService {

    OrderEntryDTO addProductToOrder(Long orderId, Long productId, int amount);

    void removeProductFromOrder(Long orderId, Long productId);

    List<OrderEntryDTO> getOrderEntries(Long orderId);
}