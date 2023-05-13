package com.gabriel.socialMedia.Service.Post;

import com.gabriel.socialMedia.Entities.Post;
import com.gabriel.socialMedia.Entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostByFollowing {
    private UUID userId;
    private Post post;

}
