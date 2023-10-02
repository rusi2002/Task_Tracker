package peaksoft.house.tasktrackerb9.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {

    private Long itemId;
    private String title;
    private Boolean isDone;

}