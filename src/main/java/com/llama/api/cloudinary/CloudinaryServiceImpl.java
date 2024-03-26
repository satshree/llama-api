package com.llama.api.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImpl {

    @Autowired
    Cloudinary cloudinary;

    public Map uploadImage(MultipartFile file, String productImageID, String productID) throws IOException {
        // DEFINE FOLDER
        HashMap<Object, Object> options = new HashMap<>();
        options.put("folder", "llama/images/[" + productID + "]/[" + productImageID + "]");

        return cloudinary.uploader().upload(file.getBytes(), options);
    }

    public Map deleteImage(String publicID) throws Exception {
        return cloudinary
                .api()
                .deleteResources(
                        Collections.singletonList(publicID),
                        ObjectUtils.asMap("type", "upload", "resource_type", "image")
                );
    }
}
