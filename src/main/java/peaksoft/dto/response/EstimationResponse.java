package peaksoft.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.enums.ReminderType;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstimationResponse {

    private Long estimationId;
    @Enumerated(EnumType.STRING)
    private ReminderType reminderType;
    private ZonedDateTime startDate;
    private ZonedDateTime duetDate;
    private ZonedDateTime time;

}