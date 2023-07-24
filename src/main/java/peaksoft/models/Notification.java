package peaksoft.models;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.*;
import peaksoft.enums.NotificationType;

import java.time.ZonedDateTime;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(generator = "notification_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "notification_gen",sequenceName = "notification_seq",allocationSize = 1)
    private Long id;

    private String text;

    private String image;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "is_read")
    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @ManyToMany(cascade = {DETACH,MERGE,REFRESH})
    private List<User> users;

    @ManyToOne(cascade = {DETACH,MERGE,REFRESH})
    private Card card;

    @OneToOne(cascade = {DETACH,MERGE,REFRESH},mappedBy = "notification")
    private Estimation estimation;
}
