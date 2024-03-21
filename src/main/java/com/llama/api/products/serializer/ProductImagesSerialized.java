package com.llama.api.products.serializer;

import com.llama.api.products.models.ProductImages;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImagesSerialized {
    String id;
    String name;
    String ext;
    Integer size;
    String full_name;
    String image;

    public static List<ProductImagesSerialized> serialize(List<ProductImages> images) {
        List<ProductImagesSerialized> productImagesSerializedList = new ArrayList<>();

        for (ProductImages i : images) {
            productImagesSerializedList.add(ProductImagesSerialized.serialize(i));
        }

        return productImagesSerializedList;
    }

    public static ProductImagesSerialized serialize(ProductImages image) {
        return new ProductImagesSerialized(
                image.getId().toString(),
                image.getName(),
                image.getExt(),
                image.getSize(),
                image.getIname(),
                image.getImage()
        );
    }
}