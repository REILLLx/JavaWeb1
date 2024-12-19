package com.example.spacecatsmarket.service;


import com.example.spacecatsmarket.DTO.order.OrderDTO;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);

    OrderDTO getOrderById(Long id);

    void deleteOrder(Long id);

}
