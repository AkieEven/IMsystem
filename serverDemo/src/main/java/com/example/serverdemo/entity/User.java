package com.example.serverdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class User {
    private int userId;
    private String password;
    private String salt;
    private String email;
    private String avatar;
    private String displayName;
    private String updateTimestamp;
    private String addTimestamp;

    public User(String password, String salt, String email, String displayName, String addTimestamp) {
        this.password = password;
        this.salt = salt;
        this.email = email;
        this.displayName = displayName;
        this.addTimestamp = addTimestamp;
    }
}
