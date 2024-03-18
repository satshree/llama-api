package com.llama.api.products.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "ProductImages")
@Data
@NoArgsConstructor
public class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pimage_id", columnDefinition = "uuid", nullable = false, updatable = false)
    UUID id;

    @Column
    String name;

    @Column
    String ext;

    @Column
    Integer size;

    @Column
    String iname;

    @Column
    String image;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    Products product;
}
