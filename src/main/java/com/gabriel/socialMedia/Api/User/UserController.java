package com.gabriel.socialMedia.Api.User;

import com.gabriel.socialMedia.Service.Authentication.Security.JwtService;
import com.gabriel.socialMedia.Service.User.UserRequest;
import com.gabriel.socialMedia.Service.User.UserService;
import com.gabriel.socialMedia.Service.User.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) throws Exception {
        if(!jwtService.isValidToken(getToken(),getUserId())){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User not autehnticate");
        }
       var response  = service.createUser(userRequest);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping()
    public ResponseEntity<UserResponse> getUser(String email){
        var reponse = service.findUserByEmail(email);
        return ResponseEntity.ok().body(reponse);
    }


    @PostMapping("/photo/upload")
    public ResponseEntity uploadPhotoProfile(@RequestParam("photo") MultipartFile photo) {
        try {
            service.uploadPhotoProfile(photo);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    public String getToken(){
        var jwt =  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        return jwt.substring(7);
    }

    public String getUserId(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("UserId");

    }

}
