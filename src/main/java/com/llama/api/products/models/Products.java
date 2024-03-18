package com.llama.api.products.models;

import com.llama.api.cart.models.CartItems;
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

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ProductImages> productImages;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    List<CartItems> cartItems;
}
