package com.llama.api.products.services;

import com.llama.api.cloudinary.CloudinaryServiceImpl;
import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.products.dto.ProductImageDTO;
import com.llama.api.products.models.ProductImages;
import com.llama.api.products.models.Products;
import com.llama.api.products.repository.ProductImageRepository;
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
    ProductService productService;

    @Autowired
    CloudinaryServiceImpl cloudinaryService;

    public List<ProductImages> getAllImages(String productID) throws ResourceNotFound {
        Products product = productService.getProduct(productID);

        return productImageRepository.findByProduct(product);
    }

    public ProductImages getImage(String id) throws ResourceNotFound {
        return productImageRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        () -> new ResourceNotFound("Image does not exist")
                );
    }

    public ProductImages addImage(String productID, MultipartFile image) throws IOException, ResourceNotFound {
        // GET PRODUCT
        Products product = productService.getProduct(productID);

        // PRODUCT IMAGE OBJECT
        ProductImages pImage = new ProductImages();
        pImage.setProduct(product);
        pImage.setIname(image.getName());
        pImage.setSize((int) image.getSize());
        pImage.setName(pImage.getIname().split("\\.")[0]);
        pImage.setExt(pImage.getIname().split("\\.")[1]);

        // UPLOAD IMAGE
        String imageURL = cloudinaryService.uploadImage(image, pImage.getId().toString());
        pImage.setImage(imageURL);

        // SAVE IMAGE TO DATABASE
        return productImageRepository.save(pImage);
    }

    public ProductImages updateImage(String id, ProductImageDTO image) throws ResourceNotFound {
        ProductImages pImage = getImage(id);

        BeanUtils.copyProperties(image, pImage);
        return productImageRepository.save(pImage);
    }

    public void deleteImage(String id) {
        productImageRepository.deleteById(UUID.fromString(id));
    }
}
