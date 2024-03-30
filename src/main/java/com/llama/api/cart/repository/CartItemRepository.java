package com.llama.api.cart.repository;

import com.llama.api.cart.models.Cart;
import com.llama.api.cart.models.CartItems;
import com.llama.api.products.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItems, UUID> {
    Boolean existsByProductAndCart(Products product, Cart cart);

    Optional<CartItems> findByProductAndCart(Products product, Cart cart);
}
