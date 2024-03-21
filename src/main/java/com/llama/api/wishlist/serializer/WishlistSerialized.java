package com.llama.api.wishlist.serializer;

import com.llama.api.wishlist.models.Wishlist;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WishlistSerialized {
    List<WishlistItemSerialized> wishlists;

    public static List<WishlistItemSerialized> serialize(List<Wishlist> wishlists) {
        WishlistSerialized wishlistSerialized = new WishlistSerialized();

        for (Wishlist w : wishlists) {
            wishlistSerialized.wishlists.add(WishlistItemSerialized.serialize(w));
        }

        return wishlistSerialized.wishlists;
    }
}