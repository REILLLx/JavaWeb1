package com.example.spacecatsmarket.repository;

import com.example.spacecatsmarket.repository.entity.ProductEntity;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long>{
}
