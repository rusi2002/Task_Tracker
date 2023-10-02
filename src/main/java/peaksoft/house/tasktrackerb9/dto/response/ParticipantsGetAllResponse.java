package peaksoft.house.tasktrackerb9.dto.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantsGetAllResponse {

    List<ParticipantsResponse> participantsResponseList;

    Boolean isMy;
}
