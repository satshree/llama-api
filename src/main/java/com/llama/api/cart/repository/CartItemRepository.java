package com.llama.api.cart.repository;

import com.llama.api.cart.models.Cart;
import com.llama.api.cart.models.CartItems;
import com.llama.api.products.models.Products;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItems, UUID> {
    Boolean existsByProductAndCart(Products product, Cart cart);

    Optional<CartItems> findByProductAndCart(Products product, Cart cart);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItems i WHERE i.id = :id")
    void forceDeleteItem(UUID id);
}
