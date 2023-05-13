package com.gabriel.socialMedia.Service.User;

import com.gabriel.socialMedia.Entities.DoubleIdObjectEntity;
import com.gabriel.socialMedia.Entities.IdObjectEntity;
import com.gabriel.socialMedia.Entities.User;
import com.gabriel.socialMedia.Repository.UserRepository;
import com.gabriel.socialMedia.Service.FileUpload.FileUploadService;
import com.gabriel.socialMedia.Api.ResponseObjectService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
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

    public ResponseObjectService findFollowing(UUID id) {
        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<User> optUser = repository.findById(id);
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("user id: " + id + " not existed");
            responseObj.setPayload(null);
            return responseObj;
        } else {
            List<UUID> followingIds = optUser.get().getFollowing();
            List<User> followingAccounts = new ArrayList<>();
            if (followingIds.size() > 0) {
                for (UUID followingId : followingIds) {
                    Optional<User> optFollowingUser = repository.findById(followingId);
                    if (optFollowingUser.isPresent()) {
                        User followingUser = optFollowingUser.get();
                        followingUser.setPassword("");
                        followingAccounts.add(followingUser);
                    }
                }
                responseObj.setStatus("success");
                responseObj.setMessage("success");
                responseObj.setPayload(followingAccounts);
                return responseObj;
            } else {
                responseObj.setStatus("fail");
                responseObj.setMessage("User id " + id + " does not follow anyone");
                responseObj.setPayload(null);
                return responseObj;
            }
        }
    }

    public ResponseObjectService findFollower(UUID id) {
        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<User> optUser = repository.findById(id);
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("user id: " + id + " not existed");
            responseObj.setPayload(null);
            return responseObj;
        } else {
            List<UUID> followerIds = optUser.get().getFollower();
            List<User> followerAccounts = new ArrayList<>();
            if (followerIds.size() > 0) {
                for (UUID followerId : followerIds) {
                    Optional<User> optFollowerUser = repository.findById(followerId);
                    if (optFollowerUser.isPresent()) {
                        User followerUser = optFollowerUser.get();
                        followerUser.setPassword("");
                        followerAccounts.add(followerUser);
                    }
                }
                responseObj.setStatus("success");
                responseObj.setMessage("success");
                responseObj.setPayload(followerAccounts);
                return responseObj;
            } else {
                responseObj.setStatus("fail");
                responseObj.setMessage("User id " + id + " does not have any follower");
                responseObj.setPayload(null);
                return responseObj;
            }
        }
    }

    public ResponseObjectService update(User inputUser) {
        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<User> optUser = repository.findById(inputUser.getId());
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("user id: " + inputUser.getId() + " not existed");
            responseObj.setPayload(null);
            return responseObj;
        } else {
            User currentUser = optUser.get();
            if (passwordEncoder.matches(inputUser.getPassword(), currentUser.getPassword())) {
                inputUser.setPassword(passwordEncoder.encode(inputUser.getPassword()));
                responseObj.setPayload(repository.save(inputUser));
                responseObj.setStatus("success");
                responseObj.setMessage("success");
                return responseObj;
            } else {
                responseObj.setStatus("fail");
                responseObj.setMessage("current password is not correct");
                responseObj.setPayload(null);
                return responseObj;
            }
        }
    }

    public ResponseObjectService followUser(DoubleIdObjectEntity doubleId) {
        // id1 - followed user, id2 - follower
        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<User> optFollowedUser = repository.findById(doubleId.getId1());
        Optional<User> optFollower = repository.findById(doubleId.getId2());
        if (optFollowedUser.isEmpty() || optFollower.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("invalid user id");
            responseObj.setPayload(null);
            return responseObj;
        } else {
            User followedUser = optFollowedUser.get();
            User follower = optFollower.get();

            // add to follower list
            List<UUID> followerList = followedUser.getFollower();
            if (followerList == null) {
                followerList = new ArrayList<>();
            }
            followerList.add(follower.getId());
            followedUser.setFollower(followerList);

            // add to following list
            List<UUID> followingList = follower.getFollowing();
            if (followingList == null) {
                followingList = new ArrayList<>();
            }
            followingList.add(followedUser.getId());
            follower.setFollowing(followingList);

            repository.save(followedUser);
            repository.save(follower);

            responseObj.setStatus("success");
            responseObj.setMessage(
                    "User id " + follower.getId() + " successfully followed user id " + followedUser.getId());
            responseObj.setPayload(new IdObjectEntity(doubleId.getId1()));
            return responseObj;
        }
    }

    public ResponseObjectService unfollowUser(DoubleIdObjectEntity doubleId) {
        // id1 - followed user, id2 - follower

        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<User> optFollowedUser = repository.findById(doubleId.getId1());
        Optional<User> optFollower = repository.findById(doubleId.getId2());
        if (optFollowedUser.isEmpty() || optFollower.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("invalid user id");
            responseObj.setPayload(null);
            return responseObj;
        } else {
            User followedUser = optFollowedUser.get();
            User follower = optFollower.get();

            // add to follower list
            List<UUID> followerList = followedUser.getFollower();
            if (followerList == null) {
                followerList = new ArrayList<>();
            }
            followerList.remove(follower.getId());
            followedUser.setFollower(followerList);

            // add to following list
            List<UUID> followingList = follower.getFollowing();
            if (followingList == null) {
                followingList = new ArrayList<>();
            }
            followingList.remove(followedUser.getId());
            follower.setFollowing(followingList);

            repository.save(followedUser);
            repository.save(follower);

            responseObj.setStatus("success");
            responseObj.setMessage(
                    "User id " + follower.getId() + " successfully unfollowed user id " + followedUser.getId());
            responseObj.setPayload(new IdObjectEntity(doubleId.getId1()));
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
