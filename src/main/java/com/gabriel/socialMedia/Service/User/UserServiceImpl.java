package com.gabriel.socialMedia.Service.User;

import com.gabriel.socialMedia.Entities.User;
import com.gabriel.socialMedia.Repository.UserRepository;
import com.gabriel.socialMedia.Service.FileUpload.FileUploadService;
import com.gabriel.socialMedia.Service.ResponseObjectService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseObjectService findAll() {
        ResponseObjectService responseObj = new ResponseObjectService();
        responseObj.setPayload(repository.findAll());
        responseObj.setStatus("success");
        responseObj.setMessage("success");
        return responseObj;
    }
    public String createUser(UserRequest userRequest) throws Exception {
        var user = new User(userRequest.name, userRequest.email, userRequest.photoUri);

        if(!repository.findUserByEmail(userRequest.email).isEmpty()){
            throw new Exception("Usuário já existe");

        }
        var hash = passwordEncoder.encode(userRequest.password);
        user.setPassword(hash);
        repository.save(user);

        return user.getId().toString();
    }

    public ResponseObjectService findById(UUID id) {
        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<User> optUser = repository.findById(id);
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("user id: " + id + " not existed");
            responseObj.setPayload(null);
            return responseObj;
        } else {
            responseObj.setPayload(optUser.get());
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            return responseObj;
        }
    }

    public User getUserById(UUID id) {
        return repository.findUserById(id).get();
    }
    public User getUser(String email){
        return repository.findUserByEmail(email).get();
    }

    public void uploadPhotoProfile(MultipartFile photo) throws Exception {
        var user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        var photoUri = "";

        try {
            var fileName = user.getId() + "." + photo.getOriginalFilename()
                    .substring(photo.getOriginalFilename().lastIndexOf(".") + 1);

            photoUri = fileUploadService.upload(photo, fileName);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        user.setPhotoUri(photoUri);
        repository.save(user);
    }

}
