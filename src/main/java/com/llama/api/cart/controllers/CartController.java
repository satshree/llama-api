package com.llama.api.cart.controllers;

import com.llama.api.cart.dto.CartItemDTO;
import com.llama.api.cart.models.Cart;
import com.llama.api.cart.models.CartItems;
import com.llama.api.cart.requests.CreateCartRequest;
import com.llama.api.cart.serializer.CartItemSerialized;
import com.llama.api.cart.serializer.CartSerialized;
import com.llama.api.cart.services.CartItemService;
import com.llama.api.cart.services.CartService;
import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.users.models.Users;
import com.llama.api.users.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/website/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public ResponseEntity<CartSerialized> getCart(@RequestParam(name = "user", required = false) String user) throws ResourceNotFound {
        CartSerialized cart;

        if (user != null) {
            Users userModel = userService.getUser(user);
            if (userModel.getCart() != null) {
                cart = CartSerialized.serialize(userModel.getCart());
            } else {
                cart = CartSerialized.serialize(cartService.createCart(user));
            }
        } else {
            cart = CartSerialized.serialize(cartService.createCart());
        }

        return ResponseEntity.ok(cart);
    }

    @GetMapping("/{id}/")
    public ResponseEntity<CartSerialized> getCartByID(@PathVariable("id") String id) throws ResourceNotFound {
        return ResponseEntity.ok(cartService.getCartByIDSerialized(id));
    }

    @PostMapping("/")
    public ResponseEntity<CartSerialized> createCart(@Valid @RequestBody Optional<CreateCartRequest> createCartRequest) throws ResourceNotFound {
        CartSerialized cartSerialized;

        if (createCartRequest.isPresent()) {
            String userID = createCartRequest.get().getUserID();

            cartSerialized = CartSerialized
                    .serialize(
                            cartService.createCart(userID)
                    );
        } else {
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

        if (item.getQuantity() > 1) {
            cartItemService.updateItem(item, item.getQuantity() - 1);
        } else {
            cartItemService.deleteItem(id);
        }

        return ResponseEntity.ok(
                CartSerialized
                        .serialize(item.getCart())
        );
    }
}
