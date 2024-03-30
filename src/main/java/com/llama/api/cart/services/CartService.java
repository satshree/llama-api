package com.llama.api.cart.services;

import com.llama.api.cart.models.Cart;
import com.llama.api.cart.models.CartItems;
import com.llama.api.cart.repository.CartRepository;
import com.llama.api.cart.serializer.CartSerialized;
import com.llama.api.exceptions.ResourceNotFound;
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

    public Cart getCart(String userID) throws ResourceNotFound {
        Users user = userService.getUser(userID);

        return cartRepository
                .findByUser(user)
                .orElseThrow(
                        () -> new ResourceNotFound("Cart does not exist")
                );
    }

    public CartSerialized getCartSerialized(String userID) throws ResourceNotFound {
        return CartSerialized.serialize(getCart(userID));
    }

    public Cart getCartByUsername(String username) throws ResourceNotFound {
        Users user = userService.getUserByUsername(username);

        return cartRepository
                .findByUser(user)
                .orElseThrow(
                        () -> new ResourceNotFound("Cart does not exist")
                );
    }

    public CartSerialized getCartByUsernameSerialized(String username) throws ResourceNotFound {
        return CartSerialized.serialize(getCartByUsername(username));
    }

    public Cart getCartByID(String id) throws ResourceNotFound {
        return cartRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        () -> new ResourceNotFound("Cart does not exist")
                );
    }

    public CartSerialized getCartByIDSerialized(String id) throws ResourceNotFound {
        return CartSerialized.serialize(getCartByID(id));
    }

    public Cart createCart(String userID) throws ResourceNotFound {
        try {
            return getCart(userID);
        } catch (ResourceNotFound e) {
            Users user = userService.getUser(userID);

            Cart cart = new Cart();
            cart.setUser(user);
            cart.setUpdated(new Date());
            cart.setTotal(0.0d);

            return cartRepository.save(cart);
        }
    }

    public Cart createCart() {
        Cart cart = new Cart();
        cart.setUpdated(new Date());
        cart.setTotal(0.0d);

        return cartRepository.save(cart);
    }

    public Cart updateCart(String id) throws ResourceNotFound {
        Cart cart = getCartByID(id);

        cart.setUpdated(new Date());
        cart.setTotal(0.0d);

        for (CartItems i : cart.getCartItems()) {
            cart.setTotal(
                    cart.getTotal() + (
                            i.getProduct().getPrice() * i.getQuantity() // PRODUCT PRICE X QUANTITY
                    )
            );
        }

        return cartRepository.save(cart);
    }

    public Cart updateCart(Cart cart) throws ResourceNotFound {
        cart.setUpdated(new Date());
        cart.setTotal(0.0d);

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
