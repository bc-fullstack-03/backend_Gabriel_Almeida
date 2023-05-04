package com.gabriel.socialMedia.Service.Authentication;

import lombok.Data;

@Data
public class AuthenticateRequest {

    private String email;
    private String password;
}
