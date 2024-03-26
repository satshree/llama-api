package com.llama.api.cart.repository;

import com.llama.api.cart.models.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItems, UUID> {
}
