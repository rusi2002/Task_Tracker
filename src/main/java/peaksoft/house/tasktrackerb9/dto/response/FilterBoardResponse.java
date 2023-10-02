package peaksoft.house.tasktrackerb9.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterBoardResponse {

    private Long boardId;
    private List<CardResponse> cardResponses;

}