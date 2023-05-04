package com.gabriel.socialMedia.Service.User;

import lombok.Data;

import java.util.UUID;

@Data
public class UserRequest {
    public String name;
    public String email;
    public String password;
    public String photoUri;
    public UUID createTo;

}
