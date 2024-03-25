package com.llama.api.products.controllers;

import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.products.serializer.ProductSerialized;
import com.llama.api.products.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/website/product")
public class WebsiteProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<ProductSerialized>> getProducts(@RequestParam(required = false) String name, @RequestParam(required = false) String sku) {
        List<ProductSerialized> allProducts;

        if (name != null) {
            allProducts = productService.getProductByNameSerialized(name);
        } else if (sku != null) {
            allProducts = productService.getProductBySkuSerialized(sku);
        } else {
            allProducts = productService.getAllProductSerialized();
        }

        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/{id}/")
    public ResponseEntity<ProductSerialized> getProduct(@PathVariable("id") String id) throws ResourceNotFound {
        return ResponseEntity.ok(ProductSerialized.serialize(productService.getProduct(id)));
    }
}
