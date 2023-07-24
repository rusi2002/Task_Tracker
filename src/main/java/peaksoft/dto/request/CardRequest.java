package peaksoft.dto.request;

import lombok.Builder;

@Builder
public record CardRequest(
        Long columnId,

        String title,

        String description
) {
}