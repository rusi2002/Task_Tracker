package peaksoft.dto.request;

import lombok.Builder;

@Builder
public record UpdateCardRequest (Long cardId,
                                 String title,
                                 String description){

}