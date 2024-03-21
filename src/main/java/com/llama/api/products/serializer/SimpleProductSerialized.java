package com.llama.api.products.serializer;

import com.llama.api.products.models.Products;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleProductSerialized {
    String id;
    String name;
    String sku;
    Double price;
    ProductCategorySerialized category;
    ProductImagesSerialized image;

    public static SimpleProductSerialized serialize(Products product) {
        SimpleProductSerialized simpleProductSerialized = new SimpleProductSerialized();

        simpleProductSerialized.setId(product.getId().toString());
        simpleProductSerialized.setName(product.getName());
        simpleProductSerialized.setSku(product.getSku());
        simpleProductSerialized.setPrice(product.getPrice());
        simpleProductSerialized.setCategory(
                ProductCategorySerialized.serialize(product.getCategory()
                )
        );

        try {
            simpleProductSerialized.setImage(
                    ProductImagesSerialized.serialize(product.getProductImages().get(0)
                    )
            );
        } catch (IndexOutOfBoundsException e) {
            simpleProductSerialized.setImage(null);
        }

        return simpleProductSerialized;
    }
}
