package com.gabriel.socialMedia.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Post")
public class Post {

    @Id
    private UUID id;
    private UUID userId;
    private String content;

    List<String> love = new ArrayList<>();
    List<String> share = new ArrayList<>();
    List<Comment> comment =  new ArrayList<>();

}
