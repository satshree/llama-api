package com.llama.api.products.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    String name;

    Double price;

    String description;

    String sku;

    @JsonProperty("category_id")
    String categoryID;
}
