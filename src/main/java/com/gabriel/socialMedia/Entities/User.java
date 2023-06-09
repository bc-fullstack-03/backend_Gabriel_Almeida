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
@Document(collection = "User")
public class User {
    @Id
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String photoUri;

    List<UUID> following = new ArrayList<>();
    List<UUID> follower = new ArrayList<>();

    public User( String name, String email, String photoUri) {
        this.setId();
        this.name = name;
        this.email = email;
        this.photoUri = photoUri;
    }

    protected void setId(){
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return this.id;
    }
}
