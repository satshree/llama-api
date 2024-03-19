package com.llama.api.products.services;

import com.llama.api.products.dto.ProductDTO;
import com.llama.api.products.models.Products;
import com.llama.api.products.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServices {
    @Autowired
    ProductRepository productRepository;

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public Products getProduct(String id) {
        UUID productId = UUID.fromString(id);

        return productRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        // implement later
                );
    }

    public Products getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public Products addProduct(ProductDTO product) {
        Products productModel = new Products();

        BeanUtils.copyProperties(product, productModel);

        return productRepository.save(productModel);
    }

    public Products updateProduct(String id, ProductDTO product) {
        Products productModel = productRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        // implement later
                );

        BeanUtils.copyProperties(product, productModel);

        return productRepository.save(productModel);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(UUID.fromString(id));
    }
}
