package com.gabriel.socialMedia.Repository;

import com.gabriel.socialMedia.Entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface PostRepository extends MongoRepository<Post, UUID> {
}
