package com.gabriel.socialMedia.Service.User;

import com.gabriel.socialMedia.Entities.User;
import com.gabriel.socialMedia.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

   // @Autowired
   // private PasswordEncoder passwordEncoder;


    public String createUser(UserRequest userRequest) throws Exception {
        var user = new User(userRequest.name, userRequest.email, userRequest.photoUri);

        if(!repository.findUserByEmail(userRequest.email).isEmpty()){
            throw new Exception("Usuário já existe");

        }
        //var hash = passwordEncoder.encode(userRequest.password);
       // user.setPassword(hash);
        repository.save(user);

        return user.getId().toString();
    }

    public UserResponse findUserByEmail(String email){
        var user = repository.findUserByEmail(email).get();
        var response = new UserResponse(user.getId(),user.getName(), user.getEmail());
        return response;
    }

    public User getUser(String email){
        return repository.findUserByEmail(email).get();
    }
    public User getUserById(UUID id) {
        return repository.findUserById(id).get();
    }

}
