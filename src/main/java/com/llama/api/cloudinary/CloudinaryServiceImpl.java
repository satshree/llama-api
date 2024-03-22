package com.llama.api.cloudinary;

import com.cloudinary.Cloudinary;
import com.llama.api.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryServiceImpl {

    @Autowired
    Cloudinary cloudinary;

    public Map uploadImage(MultipartFile file, String productImageID) throws IOException {
        // DEFINE FOLDER
        HashMap<Object, Object> options = new HashMap<>();
        options.put("folder", "llama/images/[" + productImageID + "]");

        return cloudinary.uploader().upload(file.getBytes(), options);
    }
}
