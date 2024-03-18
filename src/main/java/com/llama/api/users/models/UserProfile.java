package com.llama.api.users.models;

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

    @OneToOne
    @JoinColumn(name = "user_id")
    Users user;
    // Cart
}
