package com.llama.api.products.services;

import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.products.models.ProductCategory;
import com.llama.api.products.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductCategoryService {
    @Autowired
    ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getAllCategories() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory getCategory(String id) throws ResourceNotFound {
        return productCategoryRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        () -> new ResourceNotFound("Category does not exist")
                );
    }

    public ProductCategory addCategory(String name) {
        ProductCategory category = new ProductCategory();

        category.setName(name);

        return productCategoryRepository.save(category);
    }

    public ProductCategory updateCategory(String id, String name) throws ResourceNotFound {
        ProductCategory category = getCategory(id);
        category.setName(name);

        return productCategoryRepository.save(category);
    }

    public void deleteCategory(String id) {
        productCategoryRepository.deleteById(UUID.fromString(id));
    }
}
