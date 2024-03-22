package com.llama.api.products.controllers;

import com.llama.api.products.serializer.ProductCategorySerialized;
import com.llama.api.products.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/website/product-category")
public class WebsiteCategoryController {
    @Autowired
    ProductCategoryService productCategoryService;

    @GetMapping("/")
    public ResponseEntity<List<ProductCategorySerialized>> getCategories() {
        return ResponseEntity.ok(productCategoryService.getAllCategorySerialized());
    }
}
