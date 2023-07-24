package peaksoft.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class CardResponse {

    private Long id;
    private String title;

    public CardResponse(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}