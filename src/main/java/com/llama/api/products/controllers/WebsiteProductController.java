package com.llama.api.products.controllers;

import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.products.serializer.ProductSerialized;
import com.llama.api.products.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/website/product")
public class WebsiteProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<ProductSerialized>> getProducts(@RequestParam(required = false) String name, @RequestParam(required = false) String sku) {
        List<ProductSerialized> allProducts = new ArrayList<>();

        if (name != null) {
            allProducts.addAll(productService.getProductByNameSerialized(name));
        } else if (sku != null) {
            allProducts.add(productService.getProductBySku(sku));
        } else {
            allProducts.addAll(productService.getAllProductSerialized());
        }

        return ResponseEntity.ok(allProducts);
    }

    @GetMapping("/{id}/")
    public ResponseEntity<ProductSerialized> getProduct(@PathVariable("id") String id) throws ResourceNotFound {
        return ResponseEntity.ok(ProductSerialized.serialize(productService.getProduct(id)));
    }
}
