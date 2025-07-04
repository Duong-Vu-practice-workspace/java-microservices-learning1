package vn.edu.ptit.duongvct.practice1.socialmedia.post_service.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.domain.Post;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.dto.request.RequestPostDTO;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.dto.request.PostDTO;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.repository.PostRepository;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.service.PostService;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper mapper;
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public Post addPost(RequestPostDTO dto) {
        Post post = this.mapper.map(dto, Post.class);
        return this.postRepository.save(post);
    }

    @Override
    public Post editPost(PostDTO post) {
        Optional<Post> postDb = this.postRepository.findById(post.getPostId());
        if (postDb.isEmpty()) {
            throw new IllegalArgumentException("Post not found");
        }
        return this.postRepository.save(this.mapper.map(post, Post.class));
    }

    @Override
    @CircuitBreaker(name = "voteService", fallbackMethod = "deletePostCallback")
    public void deletePost(String id) {
        Optional<Post> postDb = this.postRepository.findById(id);
        if (postDb.isEmpty()) {
            throw new IllegalArgumentException("Post not found");
        }
        this.postRepository.deleteById(id);
    }
    private boolean deletePostCallback(String id, Exception exception) throws Exception {
        throw new Exception("Cannot delete a post. The service is currently unavailable");
    }
    @Override
    public Post getPost(String id) {
        Optional<Post> postDb = this.postRepository.findById(id);
        if (postDb.isEmpty()) {
            throw new IllegalArgumentException("Post not found");
        }
        return postDb.get();
    }

    @Override
    public List<Post> getAllPost() {
        return this.postRepository.findAll();
    }
}
