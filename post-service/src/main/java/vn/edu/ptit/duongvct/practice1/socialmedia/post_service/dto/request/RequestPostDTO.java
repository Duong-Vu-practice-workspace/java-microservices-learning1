package vn.edu.ptit.duongvct.practice1.socialmedia.post_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RequestPostDTO {
    private String title;
    private List<String> topics;
    private String body;
}
