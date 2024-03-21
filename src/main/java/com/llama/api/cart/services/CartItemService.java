package com.llama.api.cart.services;

import com.llama.api.cart.models.Cart;
import com.llama.api.cart.models.CartItems;
import com.llama.api.cart.repository.CartItemRepository;
import com.llama.api.cart.serializer.CartItemSerialized;
import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.products.models.Products;
import com.llama.api.products.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartService cartService;

    @Autowired
    ProductService productService;

    public CartItems getItem(String id) throws ResourceNotFound {
        return cartItemRepository
                .findById(
                        UUID.fromString(id)
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

        CartItems item = new CartItems();
        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(quantity);

        CartItems result = cartItemRepository.save(item);

        // UPDATE CART
        cartService.updateCart(cartID);

        return result;
    }

    public CartItems updateItem(String id, Integer quantity) throws ResourceNotFound {
        CartItems item = getItem(id);
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
