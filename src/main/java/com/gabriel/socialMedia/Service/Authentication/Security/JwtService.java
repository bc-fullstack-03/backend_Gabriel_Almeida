package com.gabriel.socialMedia.Service.Authentication.Security;

import java.util.UUID;

public interface JwtService {
    String generateToken(UUID userId);
    boolean isValidToken(String token, String userId);
}
