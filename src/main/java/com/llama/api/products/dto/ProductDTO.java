package com.llama.api.products.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    String name;

    Double price;

    String description;

    String sku;
}
