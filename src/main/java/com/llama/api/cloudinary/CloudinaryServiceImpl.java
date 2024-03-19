package com.llama.api.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImpl {

    @Autowired
    Cloudinary cloudinary;

    public String uploadImage(MultipartFile file, String productImageID) throws IOException {
        // DEFINE FOLDER
        HashMap<Object, Object> options = new HashMap<>();
        options.put("folder", "llama/images/[" + productImageID + "]");

        Map result = cloudinary.uploader().upload(file, options);

        return result.get("url").toString();
    }
}
