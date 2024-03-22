package com.llama.api.products.controllers;

import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.products.requests.ProductImageRequest;
import com.llama.api.products.serializer.ProductImagesSerialized;
import com.llama.api.products.services.ProductImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/management/product-image")
public class ProductImageController {
    @Autowired
    ProductImageService productImageService;

    @GetMapping("/{productID}/")
    public ResponseEntity<List<ProductImagesSerialized>> getImages(@PathVariable("productID") String productID) throws ResourceNotFound {
        return ResponseEntity.ok(productImageService.getAllImageSerialized(productID));
    }

    @PostMapping("/{productID}/")
    public ResponseEntity<Map<String, Object>> uploadImages(@PathVariable("productID") String productID, @Valid @ModelAttribute ProductImageRequest productImageRequest) {
        List<ProductImagesSerialized> uploadedImages = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (MultipartFile image : productImageRequest.getImages()) {
            try {
                uploadedImages.add(
                        ProductImagesSerialized.serialize(
                                productImageService.addImage(
                                        productID,
                                        image
                                )
                        )
                );
            } catch (Exception e) {
                errors.add(e.getMessage());
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("uploadedImages", uploadedImages);
        response.put("errors", errors);

        return ResponseEntity.ok(response);
    }
}
