package com.llama.api.products.services;

import com.llama.api.products.models.ProductCategory;
import com.llama.api.products.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

public class ProductCategoryService {
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getAllCategories() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory getCategory(String id) {
        return productCategoryRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        // implement later
                );
    }

    public ProductCategory addCategory(String name) {
        ProductCategory category = new ProductCategory();

        category.setName(name);

        return productCategoryRepository.save(category);
    }

    public ProductCategory updateCategory(String id, String name) {
        ProductCategory category = getCategory(id);
        category.setName(name);

        return productCategoryRepository.save(category);
    }

    public void deleteCategory(String id) {
        productCategoryRepository.deleteById(UUID.fromString(id));
    }
}
