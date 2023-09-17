package com.labreportapp.labreport.util;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author thangncph26123
 */
public class Main {


    public static void main(String[] args) {
        // Chuỗi "keytoken_NVT25102002" làm đầu vào
        String inputString = "keytoken_NVT25102002";

        try {
            // Tạo đối tượng MessageDigest với thuật toán SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Chuyển chuỗi thành mảng byte
            byte[] inputBytes = inputString.getBytes(StandardCharsets.UTF_8);

            // Tính toán giá trị băm (hash) từ mảng byte
            byte[] hashBytes = digest.digest(inputBytes);

            // Chuyển đổi mảng byte thành chuỗi hex sử dụng Apache Commons Codec
            String hexString = Hex.encodeHexString(hashBytes);

            // In ra chuỗi khóa bí mật 256 bits
            System.out.println("Chuỗi khóa bí mật 256 bits: " + hexString);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
