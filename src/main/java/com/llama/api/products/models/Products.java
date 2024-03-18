package com.llama.api.products.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Products")
@Data
@NoArgsConstructor
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", columnDefinition = "uuid", nullable = false, updatable = false)
    UUID id;

    @Column
    String name;

    @Column
    Double price;

    @Column
    String description;

    @Column
    String sku;

    @OneToMany(mappedBy = "product")
    List<ProductImages> productImages;
}
