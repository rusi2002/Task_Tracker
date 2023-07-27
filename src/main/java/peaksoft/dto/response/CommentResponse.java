package peaksoft.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

    private Long commentId;
    private String comment;
    private Date createdDate;
    private CommentUserResponse commentUserResponse;

    public CommentResponse(Long commentId, String comment, Date createdDate) {
        this.commentId = commentId;
        this.comment = comment;
        this.createdDate = createdDate;
    }
}