package com.llama.api.wishlist.controllers;

import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.wishlist.requests.WishlistRequest;
import com.llama.api.wishlist.serializer.WishlistSerialized;
import com.llama.api.wishlist.services.WishlistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    @Autowired
    WishlistService wishlistService;

    @GetMapping("/")
    public ResponseEntity<List<WishlistSerialized>> getWishlist() throws ResourceNotFound {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(
                wishlistService.getAllWishlistByUsernameSerialized(username)
        );
    }

    @PostMapping("/")
    public ResponseEntity<WishlistSerialized> addToWishlist(@Valid @RequestBody WishlistRequest wishlistRequest) throws ResourceNotFound {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return ResponseEntity.ok(
                WishlistSerialized.serialize(
                        wishlistService.addWishlistByUsername(
                                wishlistRequest.getNotes(),
                                wishlistRequest.getProductID(),
                                username
                        )
                )
        );
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<Map<String, String>> removeFromWishlist(@PathVariable("id") String id) {
        wishlistService.deleteWishlist(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Wishlist removed");

        return ResponseEntity.ok(response);
    }
}
