package vn.edu.ptit.duongvct.practice1.socialmedia.post_service.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "comments")
public class Comment {
    @Id
    private String commentId;
    private String body;
    private int upvotes;
    private int downvotes;
    private String postId;
}
