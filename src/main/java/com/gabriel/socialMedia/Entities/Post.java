package com.gabriel.socialMedia.Entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private UUID id;
    private UUID userId;
    private UUID originalUserId;
    private String content;
    private String image;
    private Instant createdAt;

    List<UUID> like = new ArrayList<>();
    List<Comment> comment =  new ArrayList<>();

    public Post( UUID userId, UUID originalUserId, String content, String image, Instant createdAt) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.originalUserId = originalUserId;
        this.content = content;
        this.image = image;
        this.createdAt = createdAt;
    }


}

