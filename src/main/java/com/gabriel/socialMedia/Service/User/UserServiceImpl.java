package com.gabriel.socialMedia.Service.User;

import com.gabriel.socialMedia.Entities.User;
import com.gabriel.socialMedia.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    public String createUser(UserRequest userRequest){
        var user = new User(userRequest.name, userRequest.email, userRequest.password);
        repository.save(user);
        return user.getId().toString();
    }

    public UserResponse findUserByEmail(String email){
        var user = repository.findUserByEmail(email).get();
        var response = new UserResponse(user.getId(),user.getName(), user.getEmail());
        return response;
    }

}
