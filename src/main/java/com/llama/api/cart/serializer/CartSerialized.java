package com.llama.api.cart.serializer;

import com.llama.api.Utils;
import com.llama.api.cart.models.Cart;
import com.llama.api.users.serializer.SimpleUserSerialized;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartSerialized {
    String id;
    String updated;
    Double total;
    SimpleUserSerialized user;
    List<CartItemSerialized> items;

    public static CartSerialized serialize(Cart cart) {
        SimpleUserSerialized user = null;
        List<CartItemSerialized> items = new ArrayList<>();

        if (cart.getUser() != null) {
            user = SimpleUserSerialized.serialize(cart.getUser());
        }

        if (cart.getCartItems() != null) {
            items = CartItemSerialized.serialize(cart.getCartItems());
        }
        return new CartSerialized(
                cart.getId().toString(),
                Utils.parseDate(cart.getUpdated()),
                cart.getTotal(),
                user,
                items
        );
    }
}
