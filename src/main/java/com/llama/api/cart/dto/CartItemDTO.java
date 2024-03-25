package com.llama.api.cart.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    @JsonProperty("product_id")
    String productID;
    @JsonProperty("cart_id")
    String cartID = null;
    Integer quantity = 1;
}
