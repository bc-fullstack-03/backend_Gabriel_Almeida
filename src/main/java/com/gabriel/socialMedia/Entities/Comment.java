package com.gabriel.socialMedia.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    private String id;
    private UUID authorId;
    private String authorName;
    private String content;
    private Instant createdAt;

    //List<UUID> like = new ArrayList<>();

    public Comment(String authorName, String content, Instant createdAt) {
        this.authorName = authorName;
        this.content = content;
        this.createdAt = createdAt;
    }


}
