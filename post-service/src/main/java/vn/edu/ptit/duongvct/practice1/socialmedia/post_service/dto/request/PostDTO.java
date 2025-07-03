package vn.edu.ptit.duongvct.practice1.socialmedia.post_service.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class PostDTO {
    private String postId;
    private String title;
    private List<String> topics;
    private int upvotes;
    private int downvotes;
    private String body;
}
