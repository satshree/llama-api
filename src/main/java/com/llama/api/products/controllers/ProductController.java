package com.llama.api.products.controllers;

import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.products.dto.ProductDTO;
import com.llama.api.products.serializer.ProductSerialized;
import com.llama.api.products.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/management/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<ProductSerialized>> getProducts(@RequestParam(required = false) String name, @RequestParam(required = false) String sku) {
        List<ProductSerialized> productSerializedList;

        if (name != null) {
            productSerializedList = productService.getProductByNameSerialized(name);
        } else if (sku != null) {
            productSerializedList = productService.getProductBySkuSerialized(sku);
        } else {
            productSerializedList = productService.getAllProductSerialized();
        }

        return ResponseEntity.ok(productSerializedList);
    }

    @GetMapping("/{id}/")
    public ResponseEntity<ProductSerialized> getProduct(@PathVariable("id") String id) throws ResourceNotFound {
        return ResponseEntity.ok(productService.getProductSerialized(id));
    }

    @PostMapping("/")
    public ResponseEntity<ProductSerialized> createProduct(@Valid @RequestBody ProductDTO productRequest) throws ResourceNotFound {
        return ResponseEntity.ok(
                ProductSerialized.serialize(
                        productService.addProduct(productRequest)
                )
        );
    }

    @PutMapping("/{id}/")
    public ResponseEntity<ProductSerialized> editProduct(@PathVariable("id") String id, @Valid @RequestBody ProductDTO productRequest) throws ResourceNotFound {
        return ResponseEntity.ok(
                ProductSerialized.serialize(
                        productService.updateProduct(id, productRequest)
                )
        );
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable("id") String id) {
        productService.deleteProduct(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Product deleted");

        return ResponseEntity.ok(response);
    }
}
