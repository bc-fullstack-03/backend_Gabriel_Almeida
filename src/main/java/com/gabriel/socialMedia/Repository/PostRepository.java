package com.gabriel.socialMedia.Repository;

import com.gabriel.socialMedia.Entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends MongoRepository<Post, UUID> {
    Optional<List<Post>> findByUserIdOrderByCreatedAtDesc(UUID id);
    Optional<List<Post>> findByUserId(UUID id);
}
