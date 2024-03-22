package com.llama.api.products.serializer;

import com.llama.api.products.models.Products;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSerialized {
    String id;
    String name;
    String description;
    String sku;
    Double price;
    ProductCategorySerialized category;
    List<ProductImagesSerialized> images;

    public static ProductSerialized serialize(Products product) {
        List<ProductImagesSerialized> imageList = new ArrayList<>();
        if (product.getProductImages() != null) { // HANDLE NULL IMAGE
            imageList.addAll(
                    ProductImagesSerialized.serialize(
                            product.getProductImages()
                    )
            );
        }

        return new ProductSerialized(
                product.getId().toString(),
                product.getName(),
                product.getDescription(),
                product.getSku(),
                product.getPrice(),
                ProductCategorySerialized.serialize(product.getCategory()),
                imageList
        );
    }
}
