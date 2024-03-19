package com.llama.api.products.services;

import com.llama.api.cloudinary.CloudinaryServiceImpl;
import com.llama.api.products.dto.ProductImageDTO;
import com.llama.api.products.models.ProductImages;
import com.llama.api.products.models.Products;
import com.llama.api.products.repository.ProductImageRepository;
import com.llama.api.products.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class ProductImageService {
    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CloudinaryServiceImpl cloudinaryService;

    public List<ProductImages> getAllImages(String productID) {
        Products product = productRepository.findById(UUID.fromString(productID)).orElseThrow(
                // implement later
        );

        return productImageRepository.findByProduct(product);
    }

    public ProductImages getImage(String id) {
        return productImageRepository.findById(UUID.fromString(id)).orElseThrow(
                // implement later
        );
    }

    public ProductImages addImage(String productID, MultipartFile image) throws IOException {
        // GET PRODUCT
        Products product = productRepository.findById(UUID.fromString(productID)).orElseThrow(
                // implement later
        );

        // PRODUCT IMAGE OBJECT
        ProductImages pImage = new ProductImages();
        pImage.setIname(image.getName());
        pImage.setSize((int) image.getSize());

        pImage.setName(pImage.getIname().split("\\.")[0]);
        pImage.setExt(pImage.getIname().split("\\.")[1]);

        // UPLOAD IMAGE
        String imageURL = cloudinaryService.uploadImage(image, pImage.getId().toString());
        pImage.setImage(imageURL);

        // SAVE IMAGE TO DATABASE
        productImageRepository.save(pImage);

        return pImage;
    }

    public ProductImages updateImage(String id, ProductImageDTO image) {
        ProductImages pImage = productImageRepository.findById(UUID.fromString(id)).orElseThrow(
                // implement later
        );

        BeanUtils.copyProperties(image, pImage);
        return productImageRepository.save(pImage);
    }

    public void deleteImage(String id) {
        productImageRepository.deleteById(UUID.fromString(id));
    }
}
