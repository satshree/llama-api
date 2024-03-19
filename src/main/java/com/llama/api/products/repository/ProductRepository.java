package com.llama.api.products.repository;

import com.llama.api.products.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Products, UUID> {
}
