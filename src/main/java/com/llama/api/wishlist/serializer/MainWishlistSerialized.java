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
            wishlistSerialized.wishlists.add(WishlistSerialized.serialize(w));
        }

        return wishlistSerialized.wishlists;
    }
}