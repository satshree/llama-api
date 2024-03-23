package com.llama.api.wishlist.serializer;

import com.llama.api.products.serializer.ProductSerialized;
import com.llama.api.users.serializer.SimpleUserSerialized;
import com.llama.api.wishlist.models.Wishlist;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class WishlistSerialized {
    String id;
    String notes;
    SimpleUserSerialized user;
    ProductSerialized product;

    public static WishlistSerialized serialize(Wishlist wishlist) {
        WishlistSerialized wishlistSerialized = new WishlistSerialized();

        wishlistSerialized.setId(wishlist.getId().toString());
        wishlistSerialized.setNotes(wishlist.getNotes());
        wishlistSerialized.setUser(SimpleUserSerialized.serialize(wishlist.getUser()));
        wishlistSerialized.setProduct(ProductSerialized.serialize(wishlist.getProduct()));

        return wishlistSerialized;
    }

    public static List<WishlistSerialized> serialize(List<Wishlist> wishlists) {
        List<WishlistSerialized> wishlistSerializedList = new ArrayList<>();

        for (Wishlist w : wishlists) {
            wishlistSerializedList.add(serialize(w));
        }

        return wishlistSerializedList;
    }
}