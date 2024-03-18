package com.llama.api.billings.models;

import com.llama.api.products.models.Products;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", columnDefinition = "uuid", nullable = false, updatable = false)
    UUID id;

    @Column
    String title;

    @Column(name = "unit_price")
    Double unitPrice;

    @Column
    Integer quantity;

    @Column
    Double total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    Products product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_id")
    Billings bill;
}
