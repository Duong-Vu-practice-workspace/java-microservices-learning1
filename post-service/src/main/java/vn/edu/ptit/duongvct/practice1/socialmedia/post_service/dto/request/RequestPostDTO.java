package vn.edu.ptit.duongvct.practice1.socialmedia.post_service.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class RequestPostDTO {
    private String title;
    // private String id
    private List<String> topics;
    private int upvotes;
    private int downvotes;
    private String body;
}
