package com.gabriel.socialMedia.Service.Authentication;

import com.gabriel.socialMedia.Service.Security.JwtService;
import com.gabriel.socialMedia.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Override
    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        var user = userService.getUser(request.getEmail());

        if(user == null){
            return null;
        }

        if(!user.getPassword().equals(request.getPassword())){
            return null;
        }

        var token = jwtService.generateToken(user.getId());
        var response = new AuthenticateResponse();

        response.setId(user.getId());
        response.setToken(token);

        return response;
    }
}
