package com.gabriel.socialMedia.Service.Comment;

import com.gabriel.socialMedia.Entities.Comment;
import com.gabriel.socialMedia.Entities.IdObjectEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPostRequestEntity {
    private Comment comment;
    private IdObjectEntity postId;
}
