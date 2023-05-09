package com.gabriel.socialMedia.Service.Authentication;

import com.gabriel.socialMedia.Service.Authentication.Security.JwtService;
import com.gabriel.socialMedia.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        var user = userService.getUser(request.getEmail());

        if(user == null){
            return null;
        }

        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            return null;
        }

        var token = jwtService.generateToken(user.getId());
        var response = new AuthenticateResponse();

        response.setId(user.getId());
        response.setToken(token);

        return response;
    }
}
