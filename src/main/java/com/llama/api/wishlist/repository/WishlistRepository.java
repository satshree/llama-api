package com.llama.api.wishlist.repository;

import com.llama.api.wishlist.models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WishlistRepository extends JpaRepository<Wishlist, UUID> {
}
