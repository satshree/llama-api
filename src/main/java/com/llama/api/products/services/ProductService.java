package com.llama.api.products.services;

import com.llama.api.products.dto.ProductDTO;
import com.llama.api.products.models.ProductCategory;
import com.llama.api.products.models.Products;
import com.llama.api.products.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductCategoryService productCategoryService;

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public Products getProduct(String id) {
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

        // SAVE BEFORE SETTING THE CATEGORY
        productModel = productRepository.save(productModel);

        // SET THE CATEGORY; SAVES PRODUCT AS WELL
        return setProductCategory(
                productModel.getId().toString(),
                product.getCategoryID()
        );
    }

    public Products updateProduct(String id, ProductDTO product) {
        Products productModel = getProduct(id);

        BeanUtils.copyProperties(product, productModel);

        // SAVE BEFORE UPDATING THE CATEGORY
        productModel = productRepository.save(productModel);

        // UPDATE THE CATEGORY; SAVES PRODUCT AS WELL
        return setProductCategory(
                productModel.getId().toString(),
                product.getCategoryID()
        );
    }

    public Products setProductCategory(String productID, String categoryID) {
        ProductCategory category = productCategoryService.getCategory(categoryID);
        Products product = getProduct(productID);

        product.setCategory(category);

        return productRepository.save(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(UUID.fromString(id));
    }
}
