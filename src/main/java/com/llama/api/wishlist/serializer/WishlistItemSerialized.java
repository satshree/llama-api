package com.llama.api.wishlist.serializer;

import com.llama.api.products.models.Products;
import com.llama.api.products.serializer.ProductSerialized;
import com.llama.api.users.models.Users;
import com.llama.api.wishlist.models.Wishlist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WishlistItemSerialized {
    String id;
    String notes;
    User user;
    ProductSerialized product;

    public void setUser(Users userModel) {
        user = new User(
                userModel.getId().toString(),
                userModel.getUsername()
        );
    }

    public void setProduct(Products productModel) {
        product = ProductSerialized.serialize(productModel);
    }

    public static WishlistItemSerialized serialize(Wishlist wishlist) {
        WishlistItemSerialized wishlistSerialized = new WishlistItemSerialized();

        wishlistSerialized.setId(wishlist.getId().toString());
        wishlistSerialized.setNotes(wishlist.getNotes());
        wishlistSerialized.setUser(wishlist.getUser());
        wishlistSerialized.setProduct(wishlist.getProduct());

        return wishlistSerialized;
    }
}

@Data
@AllArgsConstructor
class User {
    String id;
    String username;
}