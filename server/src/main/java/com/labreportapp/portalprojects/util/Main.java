package com.labreportapp.portalprojects.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author thangncph26123
 */
public class Main {

    public static String extractPublicId(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            String path = url.getPath(); // Lấy phần đường dẫn sau domain

            // Tách public ID từ phần đường dẫn
            String[] parts = path.split("/");
            if (parts.length > 0) {
                String publicIdWithExtension = parts[parts.length - 1]; // Lấy phần tử cuối cùng
                int dotIndex = publicIdWithExtension.lastIndexOf("."); // Tìm vị trí dấu chấm
                if (dotIndex != -1) {
                    return publicIdWithExtension.substring(0, dotIndex); // Lấy public ID (không bao gồm phần mở rộng)
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String imageUrl = "https://res.cloudinary.com/du1vwcyc3/image/upload/v1692435733/clg2go90oqipjsyg4deu.jpg";
        String publicId = extractPublicId(imageUrl);
        System.out.println("Public ID: " + publicId);
    }
}
