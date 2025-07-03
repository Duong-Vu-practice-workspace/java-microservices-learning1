package vn.edu.ptit.duongvct.practice1.socialmedia.post_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.annotation.ApiMessage;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.domain.Comment;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.domain.Post;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.dto.request.RequestCommentDTO;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.dto.request.RequestPostDTO;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.dto.request.PostDTO;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.dto.response.ResponseListCommentDTO;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.service.CommentService;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }
    @GetMapping
    @ApiMessage("Get all posts")
    public ResponseEntity<List<Post>> getAllPosts(){
        return ResponseEntity.ok(this.postService.getAllPost());
    }

    @GetMapping("/{postId}")
    @ApiMessage("Get a post")
    public ResponseEntity<Post> getPostById(@PathVariable String postId) {
        return ResponseEntity.ok(this.postService.getPost(postId));
    }
    @PutMapping
    @ApiMessage("Edit a post")
    public ResponseEntity<Post> editPost(@RequestBody PostDTO post) {
        return ResponseEntity.ok(this.postService.editPost(post));
    }
    @PostMapping
    @ApiMessage("Add a post")
    public ResponseEntity<Post> addPost(@RequestBody RequestPostDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.postService.addPost(dto));
    }
    @DeleteMapping("/{postId}")
    @ApiMessage("Delete a post")
    public ResponseEntity<Void> deletePost(@PathVariable String postId) {
        this.postService.deletePost(postId);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{postId}/comments")
    @ApiMessage("Get all comments of a post")
    public ResponseEntity<ResponseListCommentDTO> getAllCommentsOfAPost(@PathVariable String postId) {
        return ResponseEntity.ok(this.commentService.getAllComments(postId));
    }

    @PostMapping("/{postId}/comments")
    @ApiMessage("Create a comment of a post")
    public ResponseEntity<Comment> addCommentOfAPost(@PathVariable String postId, @RequestBody RequestCommentDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.commentService.addComment(dto, postId));
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    @ApiMessage("Delete a comment of a post")
    public ResponseEntity<Void> deleteCommentOfAPost(@PathVariable String postId, @PathVariable String commentId) {
        this.commentService.deleteComment(commentId, postId);
        return ResponseEntity.ok(null);
    }

}
