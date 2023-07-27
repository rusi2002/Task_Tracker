package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import peaksoft.config.security.JwtService;
import peaksoft.dto.request.CardRequest;
import peaksoft.dto.request.UpdateCardRequest;
import peaksoft.dto.response.*;
import peaksoft.enums.ReminderType;
import peaksoft.exceptions.NotFoundException;
import peaksoft.models.*;
import peaksoft.repository.*;
import peaksoft.repository.jdbctempleate.CardJdbcTemplate;
import peaksoft.service.CardService;

import java.util.Collections;
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
    private final LabelRepository labelRepository;
    private final CheckListRepository checkListRepository;
    private final EstimationRepository estimationRepository;
    private final CommentRepository commentRepository;
    private final CardJdbcTemplate cardJdbcTemplate;

    @Override
    public CardInnerPageResponse get(Long cardId){
        return cardJdbcTemplate.getAllCardInnerPage(cardId);
    }

    @Override
    public List<CardResponse> getAllCardsByColumnId(Long columnId) {
        return cardJdbcTemplate.getAllCardsByColumnId(columnId);
    }


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
        cardRepository.save(card);

//        return CardInnerPageResponse.builder()
//                .cardId(card.getId())
//                .title(card.getTitle())
//                .description(card.getDescription())
//                .isArchive(false)
//                .estimationResponse(new EstimationResponse())
//                .checklistResponses(new ArrayList<>())
//                .commentResponses(new ArrayList<>())
//                .labelResponses(new ArrayList<>())
//                .userResponses(new ArrayList<>())
//                .build();
return null;
    }


    @Override
    public List<CardResponse> getAllCards(Long columnId) {
        String sql = "select cardId, title, duration, numberUsers, numberItems, numberCompletedItems " +
                "from cards WHERE columnId = ?";

        return jdbcTemplate.query(sql, new Object[]{columnId}, (rs, rowNum) -> CardResponse.builder()
                .cardId(rs.getLong("cardId"))
                .title(rs.getString("title"))
                .duration(rs.getString("duration"))
                .numberUsers(rs.getInt("numberUsers"))
                .numberItems(rs.getInt("numberItems"))
                .numberCompletedItems(rs.getInt("numberCompletedItems"))
                .build());
    }


    @Override
    public CardInnerPageResponse getCardById(Long cardId) {

        Card card = cardRepository.findById(cardId).orElseThrow(() -> {
            log.error("Card with id: " + cardId + " not found!");
            return new NotFoundException("Card with id: " + cardId + " not found!");
        });

        if (card == null) {
            throw new NotFoundException("Card not found!");
        }
        EstimationResponse estimationResponse = getEstimationResponseByCardId(cardId);
        List<LabelResponse> labelResponses = getLabelResponsesByCardId(cardId);
        List<UserResponse> userResponses = getUserResponsesByCardId(cardId);
        List<CheckListResponse> checkListResponses = getCheckListResponsesByCardId(cardId);
        List<CommentResponse> commentResponses = getCommentResponsesByCardId(cardId);

//        return  CardInnerPageResponse.builder()
//                .cardId(card.getId())
//                .title(card.getTitle())
//                .description(card.getDescription())
//                .isArchive(card.getIsArchive())
//                .estimationResponse(estimationResponse)
//                .labelResponses(labelResponses)
//                .userResponses(userResponses)
//                .checklistResponses(checkListResponses)
//                .commentResponses(commentResponses)
//                .build();
        return null;
    }

    private EstimationResponse getEstimationResponseByCardId(Long cardId) {
        String query = "SELECT c.id AS id, c.title AS title, c.description AS cd, c.is_archive AS ia, \" +\n" +
                "                \"e.id AS estimation_id, e.start_date AS esd, e.due_date AS edd, e.time, e.reminder_type AS ert \" +\n" +
                "                \"FROM cards AS c JOIN estimations AS e ON c.id = e.card_id WHERE c.id = ?";

        EstimationResponse estimationResponse=
                jdbcTemplate.queryForObject(query,new Object[]{cardId},(rs, rowNum)->{
                    EstimationResponse estimationResponse1=new EstimationResponse();
                    estimationResponse1.setEstimationId(rs.getLong("e.id"));
                    estimationResponse1.setReminderType(ReminderType.valueOf(rs.getString("e.reminder_type")));
                    estimationResponse1.setStartDate(rs.getDate("e.start_date"));
                    estimationResponse1.setDuetDate(rs.getDate("e.due_date"));
                    estimationResponse1.setTime(rs.getTime("e.time"));
                    return estimationResponse1;
                } );
        return estimationResponse;
//        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> new EstimationResponse(
//                rs.getLong("e.id"),
//                rs.getDate("e.start_date"),
//                rs.getDate("e.due_date"),
//                rs.getTime("e.time")),
//                cardId);
    }

    private List<LabelResponse> getLabelResponsesByCardId(Long cardId) {

        String labelQuery = "select * from  labels l join labels_cards lc on l.id = lc.labels_id where lc.cards_id = ?";

        return Collections.singletonList(jdbcTemplate.queryForObject(labelQuery, (rs, rowNum) -> new LabelResponse(
                        rs.getLong("labelId"),
                        rs.getString("name"),
                        rs.getString("color")),
                        cardId));

    }

    private List<UserResponse> getUserResponsesByCardId(Long cardId) {

        String userQuery = "select * from users u join cards_users cu on u.id = cu.users_id where cu.cards_id = ?";

        return Collections.singletonList(jdbcTemplate.queryForObject(userQuery,(rs, rowNum) -> new UserResponse(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("image")),
                cardId));

    }

    private List<CheckListResponse> getCheckListResponsesByCardId(Long cardId) {

        String checkQuery = "select * from check_lists cl join cards c on c.id = cl.card_id where c.id = ?";

        return Collections.singletonList(jdbcTemplate.queryForObject(checkQuery,(rs, rowNum) -> new CheckListResponse(
                rs.getLong("checkListId"),
                rs.getString("description"),
                rs.getInt("percent")),
                cardId));

    }

    private List<CommentResponse> getCommentResponsesByCardId(Long cardId) {

        String commentQuery = "select * from comments c join cards c2 on c2.id = c.card_id where c2.id = ?";

        return Collections.singletonList(jdbcTemplate.queryForObject(commentQuery,(rs, rowNum) -> new CommentResponse(
                rs.getLong("commentId"),
                rs.getString("comment"),
                rs.getDate(2)),
                cardId));
    }

    //    private Card getCardByIdFromDatabase(Long cardId) {
//        String query = "select * from cards where id = ?";
//        return jdbcTemplate.queryForObject(query, new Object[]{cardId}, (rs, rowNum) -> {
//            Card card = new Card();
//            card.setId(rs.getLong("id"));
//            card.setTitle(rs.getString("title"));
//            card.setDescription(rs.getString("description"));
//            card.setIsArchive(rs.getBoolean("is_archive"));
//            return card;
//        });
//    }














    @Override
    public CardInnerPageResponse updateCardById(UpdateCardRequest updateCardRequest) {

        User user = jwtService.getAuthentication();
       Card card = cardRepository.findById(updateCardRequest.cardId()).orElseThrow(() -> {
            log.error("Card with id: " + updateCardRequest.cardId() + " not found!");
            return new NotFoundException("Card with id: " + updateCardRequest.cardId() + " not found!");
        });

            card.setTitle(updateCardRequest.title());
            card.setDescription(updateCardRequest.description());
            cardRepository.save(card);

            log.info("Card updated");
        return CardInnerPageResponse.builder()
                .cardId(updateCardRequest.cardId())
                .title(updateCardRequest.title())
                .description(updateCardRequest.description())
                .build();

    }

    @Override
    public SimpleResponse deleteCard(Long id) {

        User user = jwtService.getAuthentication();
        Card card = cardRepository.findById(id).orElseThrow(() -> {
            log.error("Card with id: " + id + " not found!");
            return new NotFoundException("Card with id: " + id + " not found!");
        });
            cardRepository.delete(card);
            log.info("Card deleted!");
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Card deleted!")
                .build();
    }
}
