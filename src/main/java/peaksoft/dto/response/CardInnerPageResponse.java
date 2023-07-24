package peaksoft.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardInnerPageResponse {

    private Long cardId;
    private String title;
    private String description;
    private Boolean isArchive;
    private EstimationResponse estimationResponse;
    private List<LabelResponse> labelResponses;
    private List<UserResponse> userResponses;
    private List<CheckListResponse> checklistResponses;
    private List<CommentResponse> commentResponses;

}