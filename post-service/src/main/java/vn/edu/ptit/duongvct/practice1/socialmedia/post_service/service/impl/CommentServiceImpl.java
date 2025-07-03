package vn.edu.ptit.duongvct.practice1.socialmedia.post_service.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.domain.Comment;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.dto.request.RequestCommentDTO;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.dto.response.ResponseListCommentDTO;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.repository.CommentRepository;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.service.CommentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ModelMapper mapper;
    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Override
    public Comment addComment(RequestCommentDTO dto, String postId) {
        Comment comment = this.mapper.map(dto, Comment.class);
        comment.setPostId(postId);
        return this.commentRepository.save(comment);
    }

    @Override
    public void deleteComment(String commentId, String postId) {
        Optional<Comment> commentDb = this.commentRepository.findById(commentId);
        if (commentDb.isEmpty()) {
            throw new IllegalArgumentException("Comment not found");

        } else if (!commentDb.get().getPostId().equals(postId)) {
            throw new IllegalArgumentException("Comment not in post");
        }
        this.commentRepository.deleteById(commentId);
    }

    @Override
    public ResponseListCommentDTO getAllComments(String postId) {
        ArrayList<Comment> comments = new ArrayList<>(this.commentRepository.findByPostId(postId));
        return this.mapListCommentsToDTO(comments);
    }
    private ResponseListCommentDTO mapListCommentsToDTO(ArrayList<Comment> comments) {
        if (comments.isEmpty()) {
            return ResponseListCommentDTO.builder()
                    .postId(null)
                    .comments(List.of())
                    .build();
        }
        return ResponseListCommentDTO.builder()
                .postId(comments.getFirst().getPostId())
                .comments(comments.stream().map(item -> this.mapper.map(item, RequestCommentDTO.class)).toList())
                .build();
    }
}
