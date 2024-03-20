package com.llama.api.authentication.models;

import com.llama.api.users.models.Users;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "RefreshToken")
@Data
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    Users user;

    @Column(nullable = false, unique = true)
    String token;

    @Column(nullable = false)
    Date expiryDate;
}
