package com.llama.api.wishlist.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistRequest {
    @JsonProperty("product_id")
    String productID;
    String notes = null;
}
