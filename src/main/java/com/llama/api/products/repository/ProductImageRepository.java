package com.llama.api.products.repository;

import com.llama.api.products.models.ProductImages;
import com.llama.api.products.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductImageRepository extends JpaRepository<ProductImages, UUID> {
    public List<ProductImages> findByProduct(Products product);
}
