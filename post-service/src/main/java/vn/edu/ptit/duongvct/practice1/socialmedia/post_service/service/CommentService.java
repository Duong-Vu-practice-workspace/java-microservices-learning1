package vn.edu.ptit.duongvct.practice1.socialmedia.post_service.service;

import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.domain.Comment;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.dto.request.RequestCommentDTO;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.dto.response.ResponseListCommentDTO;

public interface CommentService {
    Comment addComment(RequestCommentDTO dto, String postId);
    void deleteComment(String commentId, String postId);
    ResponseListCommentDTO getAllComments(String postId);
}
