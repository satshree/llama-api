package com.llama.api.products.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ProductCategory")
@Data
@NoArgsConstructor
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id", columnDefinition = "uuid", nullable = false, updatable = false)
    UUID id;

    @Column
    String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    List<Products> products;
}
