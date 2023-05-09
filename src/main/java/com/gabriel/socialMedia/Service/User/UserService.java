package com.gabriel.socialMedia.Service.User;


import com.gabriel.socialMedia.Entities.User;

import java.util.UUID;

public interface UserService {
    String createUser(UserRequest userRequest) throws Exception;
    UserResponse findUserByEmail(String email);
    User getUser(String email);
    User getUserById(UUID id);
}
