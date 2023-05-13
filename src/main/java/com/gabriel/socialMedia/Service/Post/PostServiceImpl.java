package com.gabriel.socialMedia.Service.Post;

import com.gabriel.socialMedia.Entities.*;
import com.gabriel.socialMedia.Repository.PostRepository;
import com.gabriel.socialMedia.Repository.UserRepository;
import com.gabriel.socialMedia.Api.ResponseObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    public ResponseObjectService insertPost(Post inputPost) {
        ResponseObjectService responseObj = new ResponseObjectService();
        inputPost.setCreatedAt(Instant.now());
        responseObj.setStatus("success");
        responseObj.setMessage("success");
        responseObj.setPayload(postRepository.save(inputPost));
        return responseObj;
    }

    public ResponseObjectService findPostByUserId(IdObjectEntity inputUserId) {
        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<List<Post>> userPostsOpt = postRepository.findByUserIdOrderByCreatedAtDesc(inputUserId.getId());
        if (userPostsOpt.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find any post from user id: " + inputUserId.getId());
            responseObj.setPayload(null);
            return responseObj;
        } else {
            List<Post> userPosts = userPostsOpt.get();
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            responseObj.setPayload(userPosts);
            return responseObj;
        }
    }

    public ResponseObjectService findPostByFollowing(IdObjectEntity inputUserId) {
        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<User> optUser = userRepository.findById(inputUserId.getId());
        if (optUser.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find any post from user id: " + inputUserId.getId());
            responseObj.setPayload(null);
            return responseObj;
        } else {
            User user = optUser.get();
            if (user.getFollowing() != null) {
                // if user followed someone, get their ids
                List<UUID> followingIds = new ArrayList<>();
                for (UUID id : user.getFollowing()) {
                    followingIds.add(id);
                }
                // based on these ids, get their equivalent posts
                List<PostByFollowing> listPosts = new ArrayList<>();
                for (UUID followingId : followingIds) {
                    // get following user info based on Id
                    User followingUser = new User();
                    Optional<User> optFollowingUser = userRepository.findById(followingId);
                    if (optFollowingUser.isPresent()) {
                        followingUser = optFollowingUser.get();
                    }

                    followingUser.setPassword("");

                    // get equivalent posts
                    Optional<List<Post>> followingPostsOpt = postRepository.findByUserId(followingId);
                    if (followingPostsOpt.isPresent()) {
                        // if followed account has any post, collect them
                        List<Post> followingPosts = followingPostsOpt.get();
                        if (followingPosts != null) {
                            for (Post item : followingPosts) {
                                listPosts.add(new PostByFollowing(followingUser.getId(), item));
                            }
                        }
                    }
                }
                Collections.sort(listPosts, (o1, o2) -> o2.getPost().getCreatedAt().compareTo(o1.getPost().getCreatedAt()));
                responseObj.setStatus("success");
                responseObj.setMessage("success");
                responseObj.setPayload(listPosts);
                return responseObj;
            } else {
                responseObj.setStatus("fail");
                responseObj.setMessage("user id: " + inputUserId.getId() + " has empty following list");
                responseObj.setPayload(null);
                return responseObj;
            }
        }
    }

    public ResponseObjectService updatePostByLove(DoubleIdObjectEntity doubleId) {
        // id 1 - post Id, id 2 - user who liked post
        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<Post> optPost = postRepository.findById(doubleId.getId1());
        if (optPost.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find post id: " + doubleId.getId1());
            responseObj.setPayload(null);
            return responseObj;
        } else {
            Post targetPost = optPost.get();
            List<UUID> loveList = targetPost.getLike();
            if (loveList == null) {
                loveList = new ArrayList<>();
            }
            // love and unlove a post
            if (!loveList.contains(doubleId.getId2())) {
                loveList.add(doubleId.getId2());
            } else {
                loveList.remove(doubleId.getId2());
            }
            targetPost.setLike(loveList);
            postRepository.save(targetPost);
            responseObj.setStatus("success");
            responseObj.setMessage("update love to the target post id: " + targetPost.getId());
            responseObj.setPayload(targetPost);
            return responseObj;
        }
    }

    public ResponseObjectService updatePostByComment(Post inputPost) {
        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<Post> optPost = postRepository.findById(inputPost.getId());
        if (optPost.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find post id: " + inputPost.getId());
            responseObj.setPayload(null);
            return responseObj;
        } else {
            inputPost.setCreatedAt(Instant.now());
            postRepository.save(inputPost);
            responseObj.setStatus("success");
            responseObj.setMessage("post is updated successfully");
            responseObj.setPayload(inputPost);
            return responseObj;
        }
    }

}
