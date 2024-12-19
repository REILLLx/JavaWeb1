package com.example.spacecatsmarket.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Table(name = "category")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "increment_sequence")
    @SequenceGenerator(name = "increment_sequence", sequenceName = "increment_sequence", allocationSize = 50)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}