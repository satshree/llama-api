package com.llama.api.products.controllers;

import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.products.requests.ProductCategoryRequest;
import com.llama.api.products.serializer.ProductCategorySerialized;
import com.llama.api.products.services.ProductCategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/management/product-category")
public class CategoryController {
    @Autowired
    ProductCategoryService productCategoryService;

    @GetMapping("/")
    public ResponseEntity<List<ProductCategorySerialized>> getCategories() {
        return ResponseEntity.ok(productCategoryService.getAllCategorySerialized());
    }

    @GetMapping("/{id}/")
    public ResponseEntity<ProductCategorySerialized> getCategory(@PathVariable("id") String id) throws ResourceNotFound {
        return ResponseEntity.ok(productCategoryService.getCategorySerialized(id));
    }

    @PostMapping("/")
    public ResponseEntity<ProductCategorySerialized> createCategory(@Valid @RequestBody ProductCategoryRequest productCategoryRequest) {
        return ResponseEntity.ok(
                ProductCategorySerialized.serialize(
                        productCategoryService.addCategory(
                                productCategoryRequest.getName()
                        )
                )
        );
    }

    @PutMapping("/{id}/")
    public ResponseEntity<ProductCategorySerialized> editCategory(@PathVariable("id") String id, @Valid @RequestBody ProductCategoryRequest productCategoryRequest) throws ResourceNotFound {
        return ResponseEntity.ok(
                ProductCategorySerialized.serialize(
                        productCategoryService.updateCategory(
                                id,
                                productCategoryRequest.getName()
                        )
                )
        );
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<Map<String, String>> deleteCategory(@PathVariable("id") String id) {
        productCategoryService.deleteCategory(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Category deleted");

        return ResponseEntity.ok(response);
    }
}
