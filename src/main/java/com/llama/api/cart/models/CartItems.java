package com.llama.api.cart.models;

import com.llama.api.products.models.Products;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "CartItems")
@Data
@NoArgsConstructor
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cartitem_id", columnDefinition = "uuid", nullable = false, updatable = false)
    UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Products product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    Cart cart;

    @Column
    Integer quantity;
}
