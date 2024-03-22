package com.llama.api.users.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.llama.api.billings.models.Billings;
import com.llama.api.cart.models.Cart;
import com.llama.api.wishlist.models.Wishlist;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
public class Users implements UserDetails {
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
    @JsonProperty(value = "last_login")
    Date lastLogin = null;

    @Column
    @JsonProperty("date_joined")
    Date dateJoined;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "profile_id")
    UserProfile userProfile;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    Cart cart;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Wishlist> wishlists;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Billings> bills;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
