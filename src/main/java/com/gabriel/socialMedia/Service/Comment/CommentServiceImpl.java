package com.gabriel.socialMedia.Service.Comment;

import com.gabriel.socialMedia.Api.ResponseObjectService;
import com.gabriel.socialMedia.Entities.Comment;
import com.gabriel.socialMedia.Entities.DoubleIdObjectEntity;
import com.gabriel.socialMedia.Entities.Post;
import com.gabriel.socialMedia.Repository.CommentRepository;
import com.gabriel.socialMedia.Repository.PostRepository;
import com.gabriel.socialMedia.Service.Post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepo;
    @Autowired
    private PostService postService;

    public ResponseObjectService insertComment(Comment inputComment, UUID
            inputPostId) {
        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<Post> optPost = postRepo.findById(inputPostId);
        if (optPost.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("cannot find target post id: " + inputPostId);
            responseObj.setPayload(null);
            return responseObj;
        } else{
            inputComment.setCreatedAt(Instant.now());
            commentRepository.save(inputComment);
            Post targetPost = optPost.get();
            List<Comment> commentList = targetPost.getComment();
            if (commentList == null) {
                commentList = new ArrayList<>();
            }
            commentList.add(inputComment);
            targetPost.setComment(commentList);
            postService.updatePostByComment(targetPost);
            responseObj.setStatus("success");
            responseObj.setMessage("success");
            responseObj.setPayload(inputComment);
            return responseObj;
        }
    }

//    public ResponseObjectService updateCommentByLike(CommentLikeRequest commentLike) {
//        // id 1 - post Id, id 2 - user who liked post
//        ResponseObjectService responseObj = new ResponseObjectService();
//        Optional<Comment> optComment = commentRepository.findById(commentLike.getCommentId());
//        if (optComment.isEmpty()) {
//            responseObj.setStatus("fail");
//            responseObj.setMessage("cannot find post id: " + commentLike.getCommentId());
//            responseObj.setPayload(null);
//            return responseObj;
//        } else {
//            Comment targetComment = optComment.get();
//            List<UUID> loveList = targetComment.getLike();
//            if (loveList == null) {
//                loveList = new ArrayList<>();
//            }
//            // love and unlove a post
//            if (!loveList.contains(commentLike.getUserID())) {
//                loveList.add(commentLike.getUserID());
//            } else {
//                loveList.remove(commentLike.getUserID());
//            }
//            targetComment.setLike(loveList);
//            commentRepository.save(targetComment);
//            responseObj.setStatus("success");
//            responseObj.setMessage("update love to the target post id: " + targetComment.getId());
//            responseObj.setPayload(targetComment);
//            return responseObj;
//        }
//    }

    public ResponseObjectService getComments(UUID inputPostId) {
        ResponseObjectService responseObj = new ResponseObjectService();
        Optional<Post> optTargetPost = postRepo.findById(inputPostId);
        if (optTargetPost.isEmpty()) {
            responseObj.setStatus("fail");
            responseObj.setMessage("fail");
            responseObj.setPayload(null);
            return responseObj;
        } else {
            Post targetPost = optTargetPost.get();
            List<Comment> commentList = targetPost.getComment();
            if (commentList.size() > 0) {
                responseObj.setStatus("success");
                responseObj.setMessage("success");
                responseObj.setPayload(commentList);
                return responseObj;
            } else {
                responseObj.setStatus("success");
                responseObj.setMessage("Post id " + inputPostId + " does not have any comment");
                responseObj.setPayload(null);
                return responseObj;
            }
        }
    }


}
