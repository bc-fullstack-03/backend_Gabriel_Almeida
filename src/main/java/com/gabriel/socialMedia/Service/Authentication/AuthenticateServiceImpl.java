package com.gabriel.socialMedia.Service.Authentication;

import com.gabriel.socialMedia.Service.Security.JwtService;
import com.gabriel.socialMedia.Service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateServiceImpl implements AuthenticationService{
    @Autowired
    private UserService _userService;
    @Autowired
    private JwtService _jwtService;
    @Autowired
    private PasswordEncoder _passwordEncoder;

    public AuthenticateServiceImpl() {
    }

    public AuthenticateResponse authenticate(AuthenticateRequest request) throws Exception {
        var user = _userService.getUser(request.getEmail());

        if (user == null) {
            return null;
        }

        if (!_passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new Exception("Credenciais inválidas!");
        }

        var token = _jwtService.generateToken(user.getId());

        var response = new AuthenticateResponse();

        response.setId(user.getId());
        response.setToken(token);

        return response;
    }
}
