package peaksoft.repository.jdbctempleate.jdbctemplateimpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import peaksoft.dto.response.*;
import peaksoft.enums.ReminderType;
import peaksoft.repository.jdbctempleate.CardJdbcTemplate;

import java.time.Duration;
import java.time.LocalDate;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class CardJdbcTemplateImpl implements CardJdbcTemplate {
    private final JdbcTemplate jdbcTemplate;


    private String getCardInnerPageQuery() {

        return """
                     SELECT     c.id AS cardId,
                                c.title AS title,
                                c.description AS description,
                                c.is_archive AS isArchive,
                                e.id AS estimationId,
                                e.start_date AS startDate,
                                e.due_date AS dueDate,
                                e.time AS time,
                                e.reminder_type AS reminderType
                                FROM cards AS c
                                LEFT JOIN estimations AS e ON c.id = e.card_id
                                WHERE c.id = ?
                """;

    }
    @Override
    public CardInnerPageResponse getAllCardInnerPage(Long cardId) {
        String query = getCardInnerPageQuery();

        List<CardInnerPageResponse> cardInnerPageResponses = jdbcTemplate.query(query, new Object[]{cardId}, (rs, rowNum) -> {
            CardInnerPageResponse cardInnerPageResponse1 = new CardInnerPageResponse();
            cardInnerPageResponse1.setCardId(rs.getLong("cardId"));
            cardInnerPageResponse1.setTitle(rs.getString("title"));
            cardInnerPageResponse1.setDescription(rs.getString("description"));
            cardInnerPageResponse1.setIsArchive(rs.getBoolean("isArchive"));

            EstimationResponse estimationResponse = new EstimationResponse();
            estimationResponse.setEstimationId(rs.getLong("estimationId"));
            estimationResponse.setStartDate(rs.getDate("startDate"));
            estimationResponse.setDuetDate(rs.getDate("dueDate"));
            estimationResponse.setTime(rs.getTime("time"));
            estimationResponse.setReminderType(ReminderType.valueOf(rs.getString("reminderType")));
            cardInnerPageResponse1.setEstimationResponse(estimationResponse);

            List<LabelResponse> labelResponses = getLabelResponsesByCardId(rs.getLong("cardId"));
            cardInnerPageResponse1.setLabelResponses(labelResponses);

            List<CheckListResponse> checkListResponses = getCheckListResponsesByCardId(rs.getLong("cardId"));
            cardInnerPageResponse1.setChecklistResponses(checkListResponses);

            List<UserResponse> userResponses = getMembersResponsesByCardId(rs.getLong("cardId"));
            cardInnerPageResponse1.setUserResponses(userResponses);

            List<CommentResponse> commentResponses = getCommentsResponsesByCardId(rs.getLong("cardId"));
            cardInnerPageResponse1.setCommentResponses(commentResponses);

            return cardInnerPageResponse1;

        });

        return cardInnerPageResponses.isEmpty() ? null : cardInnerPageResponses.get(0);
    }
    private List<LabelResponse> getLabelResponsesByCardId(Long cardId) {

        String sql = "select l.id as labelId ,l.label_name as label_name,l.color as color \n" +
                "from labels as l \n" +
                "join labels_cards lc on l.id = lc.labels_id \n" +
                "where lc.cards_id = ?";

        List<LabelResponse> labelResponses = jdbcTemplate.query(sql, new Object[]{cardId}, (rs, rowNum) -> {
            LabelResponse labelResponse = new LabelResponse();
            labelResponse.setLabelId(rs.getLong("labelId"));
            labelResponse.setName(rs.getString("label_name"));
            labelResponse.setColor(rs.getString("color"));
            return labelResponse;

        });
        return labelResponses;
    }

    private List<CheckListResponse> getCheckListResponsesByCardId(Long cardId) {

        String sql = "select cl.id as checkListId,cl.description as description,cl.percent as percent\n" +
                "    from check_lists as cl join cards c on c.id = cl.card_id where c.id = ?";

        List<CheckListResponse> checkListResponses = jdbcTemplate.query(sql, new Object[]{cardId}, (rs, rowNum) -> {
            CheckListResponse checkListResponse = new CheckListResponse();
            checkListResponse.setCheckListId(rs.getLong("checkListId"));
            checkListResponse.setDescription(rs.getString("description"));
            checkListResponse.setPercent(rs.getInt("percent"));
            return checkListResponse;

        });
        return checkListResponses;
    }

    private List<UserResponse> getMembersResponsesByCardId(Long cardId) {

        String sql = "select u.id as memberId,u.first_name as firstName,u.last_name as lastName,u.email as email, u.image as image\n" +
                "       from users as u join cards_users cu on u.id = cu.users_id where cu.cards_id = ?";

        List<UserResponse> userResponses = jdbcTemplate.query(sql, new Object[]{cardId}, (rs, rowNum) -> {
            UserResponse userResponse = new UserResponse();
            userResponse.setMemberId(rs.getLong("memberId"));
            userResponse.setFirstName(rs.getString("firstName"));
            userResponse.setLastName(rs.getString("lastName"));
            userResponse.setEmail(rs.getString("email"));
            userResponse.setImage(rs.getString("image"));

            return userResponse;
        });
        return userResponses;
    }

    private List<CommentResponse> getCommentsResponsesByCardId(Long cardId) {

        String sql = "SELECT co.id AS commentId, co.comment AS comment, co.created_date AS created_date, " +
                "u.id AS user_id, u.first_name AS firstName, u.last_name AS lastName, u.image AS image " +
                "FROM comments AS co " +
                "JOIN cards c ON c.id = co.card_id " +
                "JOIN users u ON co.user_id = u.id " +
                "WHERE c.id = ?";

        List<CommentResponse> commentResponses = jdbcTemplate.query(sql, new Object[]{cardId}, (rs, rowNum) -> {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setCommentId(rs.getLong("commentId"));
            commentResponse.setComment(rs.getString("comment"));
            commentResponse.setCreatedDate(rs.getDate("created_date"));

            CommentUserResponse userResponse = new CommentUserResponse();
            userResponse.setUserId(rs.getLong("user_id"));
            userResponse.setFirstName(rs.getString("firstName"));
            userResponse.setLastName(rs.getString("lastName"));
            userResponse.setImage(rs.getString("image"));
            commentResponse.setCommentUserResponse(userResponse);

            return commentResponse;
        });
        return commentResponses;
    }



















//            List<LabelResponse> labelResponses = new ArrayList<>();
//            List<UserResponse> userResponses = new ArrayList<>();
//            List<CheckListResponse> checklistResponses = new ArrayList<>();
//            List<CommentResponse> commentResponses = new ArrayList<>();

//            do {
//                long labelId = rs.getLong("labelId");
//                if (!rs.wasNull()) {
//                    LabelResponse labelResponse = new LabelResponse();
//                    labelResponse.setLabelId(labelId);
//                    labelResponse.setName(rs.getString("labelName"));
//                    labelResponse.setColor(rs.getString("color"));
//                    labelResponses.add(labelResponse);
//                }
//
//                long userId = rs.getLong("userId");
//                if (!rs.wasNull()) {
//                    UserResponse userResponse = new UserResponse();
//                    userResponse.setId(userId);
//                    userResponse.setFirstName(rs.getString("firstName"));
//                    userResponse.setLastName(rs.getString("lastName"));
//                    userResponse.setEmail(rs.getString("email"));
//                    userResponse.setImage(rs.getString("image"));
//                    userResponses.add(userResponse);
//                }
//
//                long checklistId = rs.getLong("checkListId");
//                if (!rs.wasNull()) {
//                    CheckListResponse checkListResponse = new CheckListResponse();
//                    checkListResponse.setCheckListId(checklistId);
//                    checkListResponse.setDescription(rs.getString("checkListDescription"));
//                    checkListResponse.setPercent(rs.getInt("checkListPercent"));
//                    checklistResponses.add(checkListResponse);
//                }
//
//                long commentId = rs.getLong("commentId");
//                if (!rs.wasNull()) {
//                    CommentResponse commentResponse = new CommentResponse();
//                    commentResponse.setCommentId(commentId);
//                    commentResponse.setComment(rs.getString("comment"));
//                    commentResponse.setCreatedDate(rs.getDate("commentCreatedDate"));
//                    commentResponses.add(commentResponse);
//                }
//            } while (rs.next());
//
//            cardInnerPageResponse1.setLabelResponses(labelResponses);
//            cardInnerPageResponse1.setUserResponses(userResponses);
//            cardInnerPageResponse1.setChecklistResponses(checklistResponses);
//            cardInnerPageResponse1.setCommentResponses(commentResponses);

    @Override
    public List<CardResponse> getAllCardsByColumnId(Long columnId) {

        String query = """
SELECT
    c.id AS cardId,
    c.title AS title,
    e.start_date AS startDate,
    e.due_date AS dueDate,
    (SELECT COUNT(*) FROM cards_users AS cu WHERE cu.cards_id = c.id) AS numberUsers,
    (
        SELECT COUNT(*)
        FROM items AS i
                 JOIN check_lists AS cl ON i.check_list_id = cl.id
        WHERE i.is_done = true AND cl.card_id = c.id
    ) AS numberCompletedItems,
    (SELECT COUNT(*) FROM check_lists AS cl WHERE cl.card_id = c.id) AS numberItems
FROM cards AS c
         LEFT JOIN estimations AS e ON c.id = e.card_id
WHERE c.column_id = ?

            """;

        List<CardResponse> cardResponses = jdbcTemplate.query(query, new Object[]{columnId}, (rs, rowNum) -> {
            CardResponse cardResponse = new CardResponse();
            cardResponse.setCardId(rs.getLong("cardId"));
            cardResponse.setTitle(rs.getString("title"));

            LocalDate startDate = rs.getDate("startDate").toLocalDate();
            LocalDate dueDate = rs.getDate("dueDate").toLocalDate();

            Duration duration = Duration.between(startDate.atStartOfDay(), dueDate.atStartOfDay());
            long days = duration.toDays();

            cardResponse.setDuration(days + " days");

            int numberUsers = rs.getInt("numberUsers");
            cardResponse.setNumberUsers(numberUsers);

            int numberItems = rs.getInt("numberItems");
            cardResponse.setNumberItems(numberItems);

            int numberCompletedItems = rs.getInt("numberCompletedItems");
            cardResponse.setNumberCompletedItems(numberCompletedItems);

            List<LabelResponse> labelResponses = getLabelResponsesForCard(rs.getLong("cardId"));
            cardResponse.setLabelResponses(labelResponses);

            List<CommentResponse> commentResponses = getCommentResponsesForCard(rs.getLong("cardId"));
            cardResponse.setCommentResponses(commentResponses);

            return cardResponse;
        });

        return cardResponses;
    }

    private List<LabelResponse> getLabelResponsesForCard(Long cardId) {

        String sql = "select l.id as labelId ,l.label_name as name,l.color as color from labels" +
                "    as l join labels_cards lc on l.id = lc.labels_id where lc.cards_id = ?";


        List<LabelResponse> labelResponses = jdbcTemplate.query(sql, new Object[]{cardId}, (rs, rowNum) -> {
            LabelResponse labelResponse = new LabelResponse();
            labelResponse.setLabelId(rs.getLong("labelId"));
            labelResponse.setName(rs.getString("name"));
            labelResponse.setColor(rs.getString("color"));
            return labelResponse;

        });
        return labelResponses;
    }

    private List<CommentResponse> getCommentResponsesForCard(Long cardId) {

        String query = "SELECT co.id AS commentId, co.comment AS comment, co.created_date AS createdDate, " +
                "u.id AS userId, u.first_name AS firstName, u.last_name AS lastName, u.image AS image " +
                "FROM comments AS co " +
                "JOIN cards c ON c.id = co.card_id " +
                "JOIN users u ON co.user_id = u.id " +
                "WHERE c.id = ?";

        List<CommentResponse> commentResponses = jdbcTemplate.query(query, new Object[]{cardId}, (rs, rowNum) -> {
            CommentResponse commentResponse = new CommentResponse();
            commentResponse.setCommentId(rs.getLong("commentId"));
            commentResponse.setComment(rs.getString("comment"));
            commentResponse.setCreatedDate(rs.getDate("createdDate"));

            CommentUserResponse userResponse = new CommentUserResponse();
            userResponse.setUserId(rs.getLong("userId"));
            userResponse.setFirstName(rs.getString("firstName"));
            userResponse.setLastName(rs.getString("lastName"));
            userResponse.setImage(rs.getString("image"));

            commentResponse.setCommentUserResponse(userResponse);

            return commentResponse;
        });

        return commentResponses;
    }
}
