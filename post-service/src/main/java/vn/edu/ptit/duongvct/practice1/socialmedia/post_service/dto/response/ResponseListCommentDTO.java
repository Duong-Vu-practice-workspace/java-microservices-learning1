package vn.edu.ptit.duongvct.practice1.socialmedia.post_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.ptit.duongvct.practice1.socialmedia.post_service.dto.request.RequestCommentDTO;

import java.util.List;
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseListCommentDTO {
    private String postId;
    private List<RequestCommentDTO> comments;
}
