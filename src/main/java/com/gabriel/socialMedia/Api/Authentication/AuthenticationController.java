package com.gabriel.socialMedia.Api.Authentication;

import com.gabriel.socialMedia.Service.Authentication.AuthenticateRequest;
import com.gabriel.socialMedia.Service.Authentication.AuthenticateResponse;
import com.gabriel.socialMedia.Service.Authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/autehntication")
public class AuthenticationController {

    @Autowired
    private AuthenticationService service;

    @PostMapping()
    public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody AuthenticateRequest request){
        return ResponseEntity.ok().body(service.authenticate(request));
    }
}
