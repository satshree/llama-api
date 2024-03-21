package com.llama.api.users.models;

import com.llama.api.cart.models.Cart;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "UserProfile")
@Data
@NoArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "profile_id", columnDefinition = "uuid", nullable = false, updatable = false)
    UUID id;

    @Column
    String address;

    @Column
    String phone;
}
