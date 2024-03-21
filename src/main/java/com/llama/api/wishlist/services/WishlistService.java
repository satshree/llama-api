package com.llama.api.wishlist.services;

import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.products.models.Products;
import com.llama.api.products.services.ProductService;
import com.llama.api.users.models.Users;
import com.llama.api.users.services.UserService;
import com.llama.api.wishlist.models.Wishlist;
import com.llama.api.wishlist.repository.WishlistRepository;
import com.llama.api.wishlist.serializer.WishlistSerialized;
import com.llama.api.wishlist.serializer.WishlistItemSerialized;
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

    public List<Wishlist> getAllWishlist(String userID) throws ResourceNotFound {
        Users user = userService.getUser(userID);

        return wishlistRepository.findByUser(user);
    }

    public List<WishlistItemSerialized> getAllWishlistSerialized(String userID) throws ResourceNotFound {
        List<Wishlist> wishlists = getAllWishlist(userID);
        return WishlistSerialized.serialize(wishlists);
    }

    public Wishlist getWishlist(String id) throws ResourceNotFound {
        return wishlistRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        () -> new ResourceNotFound("Wishlist item does not exist")
                );
    }

    public WishlistItemSerialized getWishlistSerialized(String id) throws ResourceNotFound {
        Wishlist wishlist = getWishlist(id);
        return WishlistItemSerialized.serialize(wishlist);
    }

    public Wishlist addWishlist(String notes, String productID, String userID) throws ResourceNotFound {
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
