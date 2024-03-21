package com.llama.api.wishlist.serializer;

import com.llama.api.wishlist.models.Wishlist;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MainWishlistSerialized {
    List<WishlistSerialized> wishlists;

    public static List<WishlistSerialized> serialize(List<Wishlist> wishlists) {
        MainWishlistSerialized wishlistSerialized = new MainWishlistSerialized();

        for (Wishlist w : wishlists) {
            WishlistSerialized wishlistItemSerialized = new WishlistSerialized();
            wishlistItemSerialized.setId(w.getId().toString());
            wishlistItemSerialized.setNotes(w.getNotes());
            wishlistItemSerialized.setUser(w.getUser());
            wishlistItemSerialized.setProduct(w.getProduct());

            wishlistSerialized.wishlists.add(wishlistItemSerialized);
        }

        return wishlistSerialized.wishlists;
    }
}