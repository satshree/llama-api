package com.llama.api.wishlist.repository;

import com.llama.api.users.models.Users;
import com.llama.api.wishlist.models.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WishlistRepository extends JpaRepository<Wishlist, UUID> {
    public List<Wishlist> findByUser(Users user);
}
