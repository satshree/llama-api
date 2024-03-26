package com.llama.api.products.serializer;

import com.llama.api.products.models.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategorySerialized {
    String id;
    String name;

    public static ProductCategorySerialized serialize(ProductCategory category) {
        return new ProductCategorySerialized(
                category.getId().toString(),
                category.getName()
        );
    }
}
