package vn.edu.ptit.duongvct.practice1.socialmedia.post_service.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(value = "comments")
public class Comment {
    @Id
    private String commentId;
    private String body;
    private int upvotes;
    private int downvotes;
    private String postId;
}
