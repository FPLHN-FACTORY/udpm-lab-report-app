package com.labreportapptool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author hieundph25894
 */
public class GenIdStudent {
    public static void main(String[] args) {


        List<User> userList = new ArrayList<>();

        // Tạo danh sách dữ liệu
        for (int i = 26000; i <= 26700; i++) {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setName("Nguyễn Văn Hiệu");
            user.setUsername("hieunvph" + (i + 1));
            user.setEmail("hieunvph" + (i + 1) + "@fpt.edu.vn");
            userList.add(user);
        }

        // Chuyển danh sách dữ liệu thành JSON
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(userList);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

class User {
    private String id;
    private String name;
    private String username;
    private String email;

    // Getters và setters (hoặc sử dụng các annotation để tuỳ chỉnh khi sử dụng Jackson)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

