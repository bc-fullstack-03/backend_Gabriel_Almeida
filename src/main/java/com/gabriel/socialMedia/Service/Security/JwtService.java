package com.gabriel.socialMedia.Service.Security;

import java.util.UUID;

public interface JwtService {
    String generateToken(UUID userId);
    boolean isValidToken(String token, String userId);
}
