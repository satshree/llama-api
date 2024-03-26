package com.llama.api.products.repository;

import com.llama.api.products.models.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {
}
