package com.gabriel.socialMedia.Service.Comment;

import com.gabriel.socialMedia.Api.ResponseObjectService;
import com.gabriel.socialMedia.Entities.Comment;

import java.util.UUID;

public interface CommentService {
    ResponseObjectService insertComment(Comment inputComment, UUID inputPostId);
   // ResponseObjectService updateCommentByLike(CommentLikeRequest commentLike);
    ResponseObjectService getComments(UUID inputPostId);
}
