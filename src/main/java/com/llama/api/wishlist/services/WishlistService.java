package com.llama.api.wishlist.services;

import com.llama.api.products.models.Products;
import com.llama.api.products.services.ProductService;
import com.llama.api.users.models.Users;
import com.llama.api.users.services.UserService;
import com.llama.api.wishlist.models.Wishlist;
import com.llama.api.wishlist.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WishlistService {
    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    public List<Wishlist> getAllWishlist(String userID) {
        Users user = userService.getUser(userID);

        return wishlistRepository.findByUser(user);
    }

    public Wishlist getWishlist(String id) {
        return wishlistRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        // implement later
                );
    }

    public Wishlist addWishlist(String notes, String productID, String userID) {
        Products product = productService.getProduct(productID);
        Users user = userService.getUser(userID);

        Wishlist wishlist = new Wishlist();

        wishlist.setNotes(notes);
        wishlist.setUser(user);
        wishlist.setProduct(product);

        return wishlistRepository.save(wishlist);
    }

    public void deleteWishlist(String id) {
        wishlistRepository.deleteById(UUID.fromString(id));
    }
}
