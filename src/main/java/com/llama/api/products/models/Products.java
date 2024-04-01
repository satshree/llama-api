package com.llama.api.products.models;

import com.llama.api.billings.models.Orders;
import com.llama.api.cart.models.CartItems;
import com.llama.api.wishlist.models.Wishlist;
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

    @Column(length = 1000)
    String description;

    @Column
    String sku;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    ProductCategory category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<ProductImages> productImages;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    List<CartItems> cartItems;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Orders> orders;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    List<Wishlist> wishlists;
}
