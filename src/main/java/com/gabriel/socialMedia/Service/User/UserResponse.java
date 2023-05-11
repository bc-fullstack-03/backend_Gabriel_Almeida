package com.gabriel.socialMedia.Service.User;

import lombok.Data;

import java.util.UUID;

@Data
public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private String photoUri;

    public UserResponse(UUID id, String name, String email, String photoUri) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.photoUri = photoUri;
    }
}
