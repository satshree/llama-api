package com.llama.api.products.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductImageDTO {
    String name;

    String ext;

    Integer size;

    String iname;
}
