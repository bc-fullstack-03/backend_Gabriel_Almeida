package com.gabriel.socialMedia.Service.User;


import com.gabriel.socialMedia.Entities.DoubleIdObjectEntity;
import com.gabriel.socialMedia.Entities.User;
import com.gabriel.socialMedia.Api.ResponseObjectService;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService {
    String createUser(UserRequest userRequest) throws Exception;
    void uploadPhotoProfile(MultipartFile photo) throws Exception;
    User getUser(String email);
    User getUserById(UUID id);
    ResponseObjectService findAll();
    ResponseObjectService findById(UUID id);
    ResponseObjectService findFollowing(UUID id);
    ResponseObjectService findFollower(UUID id);
    ResponseObjectService update(User inputUser);
    ResponseObjectService followUser(DoubleIdObjectEntity doubleId);
    ResponseObjectService unfollowUser(DoubleIdObjectEntity doubleId);
}
