package peaksoft.repository.jdbctempleate;

import peaksoft.dto.response.CardInnerPageResponse;
import peaksoft.dto.response.CardResponse;

import java.util.List;

public interface CardJdbcTemplate {

    CardInnerPageResponse getAllCardInnerPage(Long cardId);

   List<CardResponse> getAllCardsByColumnId(Long columnId);
}
