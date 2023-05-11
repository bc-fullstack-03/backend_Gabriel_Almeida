package com.gabriel.socialMedia.Service.Authentication;

public interface AuthenticationService {
    AuthenticateResponse authenticate (AuthenticateRequest request) throws Exception;
}
