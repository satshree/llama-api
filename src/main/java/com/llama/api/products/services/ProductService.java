package com.llama.api.products.services;

import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.products.dto.ProductDTO;
import com.llama.api.products.models.ProductCategory;
import com.llama.api.products.models.Products;
import com.llama.api.products.repository.ProductRepository;
import com.llama.api.products.serializer.ProductSerialized;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<ProductSerialized> getAllProductSerialized() {
        List<ProductSerialized> productSerializedList = new ArrayList<>();

        for (Products p : getAllProducts()) {
            productSerializedList.add(ProductSerialized.serialize(p));
        }

        return productSerializedList;
    }

    public Products getProduct(String id) throws ResourceNotFound {
        return productRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        () -> new ResourceNotFound("Product does not exist")
                );
    }

    public ProductSerialized getProductSerialized(String id) throws ResourceNotFound {
        return ProductSerialized.serialize(getProduct(id));
    }

    public Products getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public ProductSerialized getProductByNameSerialized(String name) {
        return ProductSerialized.serialize(getProductByName(name));
    }

    public Products addProduct(ProductDTO product) throws ResourceNotFound {
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

    public Products updateProduct(String id, ProductDTO product) throws ResourceNotFound {
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

    public Products setProductCategory(String productID, String categoryID) throws ResourceNotFound {
        ProductCategory category = productCategoryService.getCategory(categoryID);
        Products product = getProduct(productID);

        product.setCategory(category);

        return productRepository.save(product);
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(UUID.fromString(id));
    }
}
