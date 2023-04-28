package com.gabriel.socialMedia.Service.User;


import java.util.UUID;

public interface UserService {
    String createUser(UserRequest userRequest);
    UserResponse findUserByEmail(String email);
}
