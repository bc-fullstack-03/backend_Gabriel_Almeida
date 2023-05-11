package com.gabriel.socialMedia.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Comment")
public class Comment {

    @Id
    private UUID id;
    private UUID authorId;
    private String authorName;
    private String content;
    private Instant createdAt;

    List<String> like = new ArrayList<>();

}
