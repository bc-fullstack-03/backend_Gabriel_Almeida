package com.gabriel.socialMedia.Service.Post;

import com.gabriel.socialMedia.Entities.DoubleIdObjectEntity;
import com.gabriel.socialMedia.Entities.IdObjectEntity;
import com.gabriel.socialMedia.Entities.Post;
import com.gabriel.socialMedia.Api.ResponseObjectService;

public interface PostService {
    ResponseObjectService insertPost(Post inputPost);
    ResponseObjectService findPostByUserId(IdObjectEntity inputUserId);
    ResponseObjectService findPostByFollowing(IdObjectEntity inputUserId);
    ResponseObjectService updatePostByLove(DoubleIdObjectEntity doubleId);
    ResponseObjectService updatePostByComment(Post inputPost);
}
