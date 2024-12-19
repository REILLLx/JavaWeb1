package com.example.spacecatsmarket.repository;

import com.example.spacecatsmarket.repository.entity.OrderEntryEntity;
import com.example.spacecatsmarket.repository.entity.OrderEntryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderEntryRepository extends JpaRepository<OrderEntryEntity, OrderEntryId> {
    List<OrderEntryEntity> findByOrderId(Long orderId);
}