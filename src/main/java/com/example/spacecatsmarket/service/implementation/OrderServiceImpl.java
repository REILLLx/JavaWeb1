package com.example.spacecatsmarket.service.implementation;

import com.example.spacecatsmarket.DTO.order.OrderDTO;
import com.example.spacecatsmarket.mappers.OrderMapper;
import com.example.spacecatsmarket.repository.OrderRepository;
import com.example.spacecatsmarket.repository.ProductRepository;
import com.example.spacecatsmarket.repository.entity.OrderEntity;
import com.example.spacecatsmarket.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository,
                            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long id) {
        OrderEntity order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return orderMapper.toOrderDTO(order);
    }

    @Transactional(propagation = Propagation.NESTED)
    public OrderDTO createOrder(OrderDTO orderDTO) {

        OrderEntity order = orderMapper.toOrderEntity(orderDTO);
        order.setOrderDate(LocalDateTime.now());
        OrderEntity savedOrder = orderRepository.save(order);
        return orderMapper.toOrderDTO(savedOrder);
    }

    @Transactional
    public void deleteOrder(Long orderId) {
        try {
            if (orderRepository.existsById(orderId)) {
                orderRepository.deleteById(orderId);
            }
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException("Failed to delete category due to database error", ex);
        }
    }
}
