package com.gabriel.socialMedia.Api.User;

import com.gabriel.socialMedia.Service.User.UserRequest;
import com.gabriel.socialMedia.Service.User.UserService;
import com.gabriel.socialMedia.Service.User.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping()
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) throws Exception {
       var response  = service.createUser(userRequest);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<UserResponse> getUser(String email){
        var reponse = service.findUserByEmail(email);
        return ResponseEntity.ok().body(reponse);
    }

}
