package com.gabriel.socialMedia.Service.Authentication;

import lombok.Data;

import java.util.UUID;

@Data
public class AuthenticateResponse {
    private UUID id;
    private String token;
}
