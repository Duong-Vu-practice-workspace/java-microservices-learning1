package vn.edu.ptit.duongvct.practice1.socialmedia.post_service.service;

import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.domain.Post;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.dto.request.RequestPostDTO;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.dto.request.PostDTO;

import java.util.List;

public interface PostService {
    public Post addPost(RequestPostDTO dto);
    public Post editPost(PostDTO post);
    public void deletePost(String id);
    public Post getPost(String id);
    public List<Post> getAllPost();
}
