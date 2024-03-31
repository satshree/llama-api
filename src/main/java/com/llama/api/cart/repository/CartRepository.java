package com.llama.api.cart.repository;

import com.llama.api.cart.models.Cart;
import com.llama.api.users.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    public Optional<Cart> findByUser(Users user);
}
