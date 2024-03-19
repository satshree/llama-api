package com.llama.api.users.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.llama.api.billings.models.Billings;
import com.llama.api.wishlist.models.Wishlist;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", columnDefinition = "uuid", nullable = false, updatable = false)
    UUID id;

    @Column
    @JsonProperty("first_name")
    String firstName;

    @Column
    @JsonProperty("last_name")
    String lastName;

    @Column
    String username;

    @Column
    String email;

    @Column
    String password;

    @Column
    @JsonProperty("is_super")
    Boolean isSuper;

    @Column
    @JsonProperty("is_staff")
    Boolean isStaff;

    @Column
    @JsonProperty("last_login")
    Date lastLogin;

    @Column
    @JsonProperty("date_joined")
    Date dateJoined;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    UserProfile userProfile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Wishlist> wishlists;

    @OneToMany(mappedBy = "user")
    List<Billings> bills;
}
