package com.gabriel.socialMedia.Api;

import com.gabriel.socialMedia.Entities.Comment;
import com.gabriel.socialMedia.Entities.DoubleIdObjectEntity;
import com.gabriel.socialMedia.Entities.IdObjectEntity;
import com.gabriel.socialMedia.Service.Comment.CommentLikeRequest;
import com.gabriel.socialMedia.Service.Comment.CommentPostRequestEntity;
import com.gabriel.socialMedia.Service.Comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/insertcomment")
    public ResponseEntity<ResponseObjectService> insertComment(@RequestBody CommentPostRequestEntity postedComment) {
        Comment inputComment = postedComment.getComment();
        IdObjectEntity inputPostId = postedComment.getPostId();
        return new ResponseEntity<ResponseObjectService>
                (commentService.insertComment(inputComment, inputPostId.getId()), HttpStatus.OK);
    }
    @PostMapping("/getcomments")
    public ResponseEntity<ResponseObjectService> getComments(@RequestBody IdObjectEntity inputPostId) {
        return new ResponseEntity<ResponseObjectService>(commentService.getComments(inputPostId.getId()), HttpStatus.OK);
    }
//    @PostMapping("/likecomment")
//    public ResponseEntity<ResponseObjectService> lovePost(@RequestBody CommentLikeRequest commentLike) {
//        return new ResponseEntity<ResponseObjectService>(commentService.updateCommentByLike(commentLike), HttpStatus.OK);
//    }
}
