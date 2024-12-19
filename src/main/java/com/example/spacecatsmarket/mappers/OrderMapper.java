package com.example.spacecatsmarket.mappers;

import com.example.spacecatsmarket.DTO.order.OrderDTO;
import com.example.spacecatsmarket.repository.entity.OrderEntity;
import com.example.spacecatsmarket.repository.entity.OrderEntryEntity;
import com.example.spacecatsmarket.repository.entity.ProductEntity;
import org.mapstruct.Mapper;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDTO toOrderDTO(OrderEntity order);

    OrderEntity toOrderEntity(OrderDTO orderDTO);

    default List<ProductEntity> mapOrderEntriesToProducts(List<OrderEntryEntity> orderEntries) {
        return orderEntries.stream()
                .map(OrderEntryEntity::getProduct)
                .collect(Collectors.toList());
    }
}

