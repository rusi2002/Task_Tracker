package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.config.JwtService;
import peaksoft.dto.request.CardRequest;
import peaksoft.dto.request.UpdateCardRequest;
import peaksoft.dto.response.CardInnerPageResponse;
import peaksoft.dto.response.CardResponse;
import peaksoft.dto.response.EstimationResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Card;
import peaksoft.entity.Column;
import peaksoft.entity.User;
import peaksoft.exceptions.BadCredentialException;
import peaksoft.exceptions.NotFoundException;
import peaksoft.repository.CardRepository;
import peaksoft.repository.ColumnRepository;
import peaksoft.service.CardService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final ColumnRepository columnRepository;
    private final JwtService jwtService;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public CardInnerPageResponse saveCard(CardRequest cardRequest) {
        User user = jwtService.getAuthentication();
        Column column = columnRepository.findById(cardRequest.columnId()).orElseThrow(
                () -> {
                    log.error("Column with id: " + cardRequest.columnId() + " not found!");
                    return new NotFoundException("Column with id: " + cardRequest.columnId() + " not found!");
                }
        );

        Card card = new Card();
        card.setTitle(cardRequest.title());
        card.setDescription(cardRequest.description());
        card.setIsArchive(false);
        card.setColumn(column);
        column.getCards().add(card);
        user.getCards().add(card);

        return CardInnerPageResponse.builder()
                .cardId(card.getId())
                .title(card.getTitle())
                .description(card.getDescription())
                .isArchive(false)
                .estimationResponse(new EstimationResponse())
                .checklistResponses(new ArrayList<>())
                .commentResponses(new ArrayList<>())
                .labelResponses(new ArrayList<>())
                .userResponses(new ArrayList<>())
                .build();

    }


    @Override
    public List<CardResponse> getAllCards(Long columnId) {
        return cardRepository.getAllCards();

    }

    @Override
    public CardInnerPageResponse getCardById(Long cardId) {

        String query = "";
//        Card card = jdbcTemplate.queryForObject(query, ((rs, rowNum) ->
//                new Card(rs.getLong("id"),
//                        rs.getString("title"),
//                        rs.getString("description"),
//                        rs.getBoolean("isArchive"),
//                        cardId
//                )


        return null;
    }

    @Override
    public CardInnerPageResponse updateCardById(UpdateCardRequest updateCardRequest) {
        User user = jwtService.getAuthentication();
        cardRepository.findById(updateCardRequest.cardId()).orElseThrow(() -> {
            log.error("Card with id: " + updateCardRequest.cardId() + " not found!");
            return new NotFoundException("Card with id: " + updateCardRequest.cardId() + " not found!");
        });

        Card card = new Card();
        if (card.getUsers().contains(user)) {
            card.setTitle(updateCardRequest.title());
            card.setDescription(updateCardRequest.description());
            cardRepository.save(card);
        } else {
            log.error("User not allowed");
            throw new NotFoundException("User not allowed");
        }
//        return CardInnerPageResponse.builder()
//                .cardId(card.getId())
//                .
//                .status(HttpStatus.OK)
//                .message("Card updated!")
//                .build();

        return null;
    }

    @Override
    public SimpleResponse deleteCard(Long id) {
        User user = jwtService.getAuthentication();

        Card card = cardRepository.findById(id).orElseThrow(() -> {
            log.error("Card with id: " + id + " not found!");
            return new NotFoundException("Card with id: " + id + " not found!");
        });
        if (card.getUsers().contains(user)) {
            cardRepository.delete(card);

        } else {
            log.error("User not allowed");
            throw new NotFoundException("User not allowed");
        }
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Card deleted!")
                .build();
    }
}
