package com.llama.api.wishlist.models;

import com.llama.api.products.models.Products;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.llama.api.users.models.Users;

import java.util.UUID;

@Entity
@Table(name = "Wishlist")
@Data
@NoArgsConstructor
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "list_id", columnDefinition = "uuid", nullable = false, updatable = false)
    UUID id;

    @Column
    String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    Products product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    Users user;
}
