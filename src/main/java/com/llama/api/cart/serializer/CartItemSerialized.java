package com.llama.api.cart.serializer;

import com.llama.api.cart.models.CartItems;
import com.llama.api.products.serializer.ProductSerialized;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemSerialized {
    String id;
    Integer order;
    ProductSerialized product;
    Integer quantity;
    Double total;

    public static CartItemSerialized serialize(CartItems item) {
        return new CartItemSerialized(
                item.getId().toString(),
                item.getOrder(),
                ProductSerialized.serialize(item.getProduct()),
                item.getQuantity(),
                (item.getProduct().getPrice() * item.getQuantity()) // UNIT PRICE X QUANTITY
        );
    }

    public static List<CartItemSerialized> serialize(List<CartItems> items) {
        List<CartItemSerialized> cartItemSerializedList = new ArrayList<>();

        for (CartItems i : items) {
            cartItemSerializedList.add(CartItemSerialized.serialize(i));
        }

        cartItemSerializedList.sort(Comparator.comparing(CartItemSerialized::getOrder));

        return cartItemSerializedList;
    }
}

