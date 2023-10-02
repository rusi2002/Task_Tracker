package peaksoft.house.tasktrackerb9.dto.request;

import lombok.Builder;

@Builder
public record ColumUpdateRequest(
        Long columId,
        String newTitle
) {
}
