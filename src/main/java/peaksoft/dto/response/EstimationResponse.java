package peaksoft.dto.response;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.enums.ReminderType;

import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstimationResponse {

    private Long estimationId;
    @Enumerated(EnumType.STRING)
    private ReminderType reminderType;
    private Date startDate;
    private Date duetDate;
    private Date time;

}