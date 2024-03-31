package com.llama.api.cart.models;

import com.llama.api.users.models.Users;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Cart")
@Data
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id", columnDefinition = "uuid", nullable = false, updatable = false)
    UUID id;

    @Column
    Double total;

    @Column
    Date updated;

    @OneToOne(fetch = FetchType.LAZY)
    Users user;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<CartItems> cartItems;
}
