package com.llama.api.cart.services;

import com.llama.api.cart.models.Cart;
import com.llama.api.cart.models.CartItems;
import com.llama.api.cart.repository.CartItemRepository;
import com.llama.api.cart.repository.CartRepository;
import com.llama.api.cart.serializer.CartItemSerialized;
import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.products.models.Products;
import com.llama.api.products.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional
public class CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    @Autowired
    CartRepository cartRepository;

    public CartItems getItem(String id) throws ResourceNotFound {
        return cartItemRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        () -> new ResourceNotFound("Item does not exist")
                );
    }

    public CartItems getItemByProductAndCart(String productID, String cartID) throws ResourceNotFound {
        Products product = productService.getProduct(productID);
        Cart cart = cartService.getCartByID(cartID);
        return cartItemRepository
                .findByProductAndCart(
                        product, cart
                ).orElseThrow(
                        () -> new ResourceNotFound("Item does not exist")
                );
    }

    public CartItems getItemByProductAndCart(Products product, Cart cart) throws ResourceNotFound {
        return cartItemRepository
                .findByProductAndCart(
                        product, cart
                ).orElseThrow(
                        () -> new ResourceNotFound("Item does not exist")
                );
    }

    public CartItemSerialized getItemSerialized(String id) throws ResourceNotFound {
        return CartItemSerialized.serialize(getItem(id));
    }

    public CartItems addItem(String cartID, String productID, Integer quantity) throws ResourceNotFound {
        Products product = productService.getProduct(productID);
        Cart cart = cartService.getCartByID(cartID);

        if (cartItemRepository.existsByProductAndCart(product, cart)) {
            CartItems item = getItemByProductAndCart(product, cart);

            // UPDATE CART
            cartService.updateCart(cart);

            return updateItem(item, item.getQuantity() + 1);
        } else {
            CartItems item = new CartItems();
            item.setOrder(cart.getCartItems().size() + 1);
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);

            item = cartItemRepository.save(item);

            // UPDATE CART
            cart.setTotal(product.getPrice() * item.getQuantity());
            cart.setUpdated(new Date());
            cartRepository.save(cart);

            return item;
        }

    }

    public CartItems updateItem(String id, Integer quantity) throws ResourceNotFound {
        CartItems item = getItem(id);
        item.setQuantity(quantity);

        CartItems result = cartItemRepository.save(item);

        // UPDATE CART
        cartService.updateCart(item.getCart().getId().toString());

        return result;
    }

    public CartItems updateItem(CartItems item, Integer quantity) throws ResourceNotFound {
        item.setQuantity(quantity);

        CartItems result = cartItemRepository.save(item);

        // UPDATE CART
        cartService.updateCart(item.getCart().getId().toString());

        return result;
    }

    public void deleteItem(String id) {
        cartItemRepository.deleteById(UUID.fromString(id));
    }
}
