package vn.edu.ptit.duongvct.practice1.socialmedia.post_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.domain.Post;
@Repository
public interface PostRepository extends MongoRepository<String, Post> {
}
