package com.labreportapp.util;

import java.util.Random;

/**
 * @author thangncph26123
 */
public class RandomString {

    public static String random() {
        int length = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            randomString.append(characters.charAt(randomIndex));
        }
        String result = randomString.toString();
        return result;
    }
}
