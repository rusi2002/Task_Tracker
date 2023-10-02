package peaksoft.house.tasktrackerb9.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteResponse {

    private List<FavoriteBoardResponse> boardResponses;

    private List<FavoriteWorkSpaceResponse> workSpaceResponses;
}
