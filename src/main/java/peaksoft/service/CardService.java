package peaksoft.service;

import peaksoft.dto.request.CardRequest;
import peaksoft.dto.request.UpdateCardRequest;
import peaksoft.dto.response.CardInnerPageResponse;
import peaksoft.dto.response.CardResponse;
import peaksoft.dto.response.SimpleResponse;
import java.util.List;

public interface CardService {

    CardInnerPageResponse saveCard(CardRequest cardRequest);

    List<CardResponse> getAllCards(Long columnId);

    CardInnerPageResponse getCardById(Long cardId);

    CardInnerPageResponse updateCardById(UpdateCardRequest updateCardRequest);

    SimpleResponse deleteCard(Long id);

}