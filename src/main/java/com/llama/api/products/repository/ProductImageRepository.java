package com.llama.api.products.repository;

import com.llama.api.products.models.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductImageRepository extends JpaRepository<ProductImages, UUID> {
}
