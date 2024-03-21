package com.llama.api.wishlist.serializer;

import com.llama.api.products.models.Products;
import com.llama.api.users.models.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WishlistSerialized {
    String id;
    String notes;
    User user;
    Product product;

    public void setUser(Users userModel) {
        user = new User(
                userModel.getId().toString(),
                userModel.getUsername()
        );
    }

    public void setProduct(Products productModel) {
        product = new Product(
                productModel.getId().toString(),
                productModel.getName(),
                productModel.getSku()
        );
    }
}

@Data
@AllArgsConstructor
class User {
    String id;
    String username;
}

@Data
@AllArgsConstructor
class Product {
    String id;
    String name;
    String sku;
}