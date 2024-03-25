package com.llama.api.cart.controllers;

import com.llama.api.cart.dto.CartItemDTO;
import com.llama.api.cart.models.CartItems;
import com.llama.api.cart.serializer.CartSerialized;
import com.llama.api.cart.services.CartItemService;
import com.llama.api.cart.services.CartService;
import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.users.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/website/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    UserService userService;

    @GetMapping("/{id}/")
    public ResponseEntity<CartSerialized> getCart(@PathVariable("id") String id) throws ResourceNotFound {
        return ResponseEntity.ok(cartService.getCardByIDSerialized(id));
    }

    @PostMapping("/")
    public ResponseEntity<CartSerialized> createCart() {
        CartSerialized cartSerialized;

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            cartSerialized = CartSerialized
                    .serialize(
                            cartService.createCart(
                                    userService
                                            .getUserByUsername(username)
                                            .getId()
                                            .toString()
                            )
                    );
        } catch (Exception e) {
            cartSerialized = CartSerialized
                    .serialize(
                            cartService.createCart()
                    );
        }

        return ResponseEntity.ok(cartSerialized);
    }

    @PostMapping("/{id}/")
    public ResponseEntity<CartSerialized> addToCart(@PathVariable("id") String id, @Valid @RequestBody CartItemDTO cartItemDTO) throws ResourceNotFound {
        CartItems cartItem = cartItemService.addItem(
                id,
                cartItemDTO.getProductID(),
                cartItemDTO.getQuantity()
        );

        return ResponseEntity.ok(
                CartSerialized.serialize(
                        cartItem.getCart()
                )
        );
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<CartSerialized> removeFromCart(@PathVariable("id") String id) throws ResourceNotFound {
        CartItems item = cartItemService.getItem(id);

        cartItemService.deleteItem(id);

        return ResponseEntity.ok(
                CartSerialized
                        .serialize(item.getCart())
        );
    }
}
