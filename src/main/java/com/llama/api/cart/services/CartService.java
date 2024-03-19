package com.llama.api.cart.services;

import com.llama.api.cart.models.Cart;
import com.llama.api.cart.models.CartItems;
import com.llama.api.cart.repository.CartRepository;
import com.llama.api.users.models.Users;
import com.llama.api.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserService userService;

    public Cart getCart(String userID) {
        Users user = userService.getUser(userID);

        return cartRepository.findByUser(user);
    }

    public Cart getCartByID(String id) {
        return cartRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        // implement later
                );
    }

    public Cart createCart(String userID) {
        Users user = userService.getUser(userID);

        Cart cart = new Cart();
        cart.setUser(user.getUserProfile());
        cart.setUpdated(new Date());
        cart.setTotal(0.0d);

        return cartRepository.save(cart);
    }

    public Cart updateCart(String id) {
        Cart cart = getCartByID(id);

        cart.setUpdated(new Date());

        for (CartItems i : cart.getCartItems()) {
            cart.setTotal(
                    cart.getTotal() + (
                            i.getProduct().getPrice() * i.getQuantity() // PRODUCT PRICE X QUANTITY
                    )
            );
        }

        return cartRepository.save(cart);
    }
}
