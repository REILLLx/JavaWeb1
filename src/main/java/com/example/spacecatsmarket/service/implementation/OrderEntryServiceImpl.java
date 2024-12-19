package com.example.spacecatsmarket.service.implementation;

import com.example.spacecatsmarket.DTO.order.OrderEntryDTO;
import com.example.spacecatsmarket.repository.OrderEntryRepository;
import com.example.spacecatsmarket.repository.OrderRepository;
import com.example.spacecatsmarket.repository.ProductRepository;
import com.example.spacecatsmarket.repository.entity.OrderEntity;
import com.example.spacecatsmarket.repository.entity.OrderEntryEntity;
import com.example.spacecatsmarket.repository.entity.OrderEntryId;
import com.example.spacecatsmarket.repository.entity.ProductEntity;
import com.example.spacecatsmarket.service.OrderEntryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderEntryServiceImpl implements OrderEntryService {

    private final OrderEntryRepository orderEntryRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional(propagation = Propagation.NESTED)
    public OrderEntryDTO addProductToOrder(Long orderId, Long productId, int amount) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));

        OrderEntryId id = new OrderEntryId(orderId, productId);
        OrderEntryEntity orderEntry = new OrderEntryEntity(id, order, product, amount);

        OrderEntryEntity savedOrderEntry = orderEntryRepository.save(orderEntry);

        return new OrderEntryDTO(savedOrderEntry.getOrder().getId(), savedOrderEntry.getProduct().getId(), savedOrderEntry.getAmount());
    }

    @Transactional
    public void removeProductFromOrder(Long orderId, Long productId) {
        OrderEntryId id = new OrderEntryId(orderId, productId);
        orderEntryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<OrderEntryDTO> getOrderEntries(Long orderId) {
        List<OrderEntryEntity> entries = orderEntryRepository.findByOrderId(orderId);
        return entries.stream()
                .map(entry -> new OrderEntryDTO(entry.getOrder().getId(), entry.getProduct().getId(), entry.getAmount()))
                .toList();
    }
}

