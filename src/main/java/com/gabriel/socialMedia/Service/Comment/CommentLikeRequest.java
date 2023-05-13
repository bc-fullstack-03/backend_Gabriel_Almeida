package com.gabriel.socialMedia.Service.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeRequest {
    private String commentId;
    private UUID userID;
}
