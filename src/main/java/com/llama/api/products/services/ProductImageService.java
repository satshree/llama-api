package com.llama.api.products.services;

import com.llama.api.cloudinary.CloudinaryServiceImpl;
import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.products.dto.ProductImageDTO;
import com.llama.api.products.models.ProductImages;
import com.llama.api.products.models.Products;
import com.llama.api.products.repository.ProductImageRepository;
import com.llama.api.products.serializer.ProductImagesSerialized;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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

    public List<ProductImagesSerialized> getAllImageSerialized(String productID) throws ResourceNotFound {
        return ProductImagesSerialized.serialize(getAllImages(productID));
    }

    public ProductImages getImage(String id) throws ResourceNotFound {
        return productImageRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        () -> new ResourceNotFound("Image does not exist")
                );
    }

    public ProductImagesSerialized getImageSerialized(String id) throws ResourceNotFound {
        return ProductImagesSerialized.serialize(getImage(id));
    }

    public ProductImages addImage(String productID, MultipartFile image) throws IOException, ResourceNotFound {
        // GET PRODUCT
        Products product = productService.getProduct(productID);

        // PRODUCT IMAGE OBJECT
        ProductImages pImage = new ProductImages();
        pImage.setProduct(product);
        pImage.setIname(image.getOriginalFilename());
        pImage.setSize((int) image.getSize());

        pImage.setName(pImage.getIname().split("\\.")[0]);

        pImage = productImageRepository.save(pImage);

        // UPLOAD IMAGE
        Map uploadedImage = cloudinaryService.uploadImage(image, pImage.getId().toString(), productID);
        pImage.setImage(uploadedImage.get("url").toString());
        pImage.setExt(uploadedImage.get("format").toString());
        pImage.setCloudinaryPublicID(uploadedImage.get("public_id").toString());

        // SAVE IMAGE TO DATABASE
        return productImageRepository.save(pImage);
    }

    public ProductImages updateImage(String id, ProductImageDTO image) throws ResourceNotFound {
        ProductImages pImage = getImage(id);

        BeanUtils.copyProperties(image, pImage);
        return productImageRepository.save(pImage);
    }

    public void deleteImage(String id) throws Exception {
        String publicID = getImage(id).getCloudinaryPublicID();
        productImageRepository.deleteById(UUID.fromString(id));
        cloudinaryService.deleteImage(publicID);
    }
}
