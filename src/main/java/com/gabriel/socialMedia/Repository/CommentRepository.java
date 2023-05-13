package com.gabriel.socialMedia.Repository;

import com.gabriel.socialMedia.Entities.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
