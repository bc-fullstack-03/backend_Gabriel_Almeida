package com.gabriel.socialMedia.Api;

import com.gabriel.socialMedia.Entities.IdObjectEntity;
import com.gabriel.socialMedia.Service.ResponseObjectService;
import com.gabriel.socialMedia.Service.Security.JwtService;
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

    @PostMapping()
    public ResponseEntity<ResponseObjectService> findAllUsers() {
        return new ResponseEntity<ResponseObjectService>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody UserRequest userRequest) throws Exception {
       var response  = service.createUser(userRequest);
       return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/profile")
    public ResponseEntity<ResponseObjectService> findById(@RequestBody IdObjectEntity inputId) {
        return new ResponseEntity<ResponseObjectService>(service.findById(inputId.getId()), HttpStatus.OK);
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
