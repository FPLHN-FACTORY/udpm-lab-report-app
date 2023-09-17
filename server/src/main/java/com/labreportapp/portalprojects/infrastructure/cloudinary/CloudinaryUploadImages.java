package com.labreportapp.portalprojects.infrastructure.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author thangncph26123
 */

@Component
public class CloudinaryUploadImages {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");
        } catch (Exception e) {
            e.printStackTrace();
            return "Upload failed";
        }
    }

    public String deleteImage(String publicId) {
        try {
            Map deleteResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            if (deleteResult.containsKey("result") && deleteResult.get("result").equals("ok")) {
                return "Image deleted successfully";
            } else {
                return "Image deletion failed";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Image deletion failed";
        }
    }

}
