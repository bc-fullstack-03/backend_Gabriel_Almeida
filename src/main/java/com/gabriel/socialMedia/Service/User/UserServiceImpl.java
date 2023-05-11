package com.gabriel.socialMedia.Service.User;

import com.gabriel.socialMedia.Entities.User;
import com.gabriel.socialMedia.Repository.UserRepository;
import com.gabriel.socialMedia.Service.FileUpload.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public UserResponse findUserByEmail(String email){
        var user = repository.findUserByEmail(email).get();
        var response = new UserResponse(user.getId(),user.getName(), user.getEmail(), user.getPhotoUri());
        return response;
    }

    public User getUser(String email){
        return repository.findUserByEmail(email).get();
    }
    public User getUserById(UUID id) {
        return repository.findUserById(id).get();
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
