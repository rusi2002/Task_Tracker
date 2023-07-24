package peaksoft.models;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(generator = "comment_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "comment_gen", sequenceName = "comment_seq", allocationSize = 1)
    private Long id;

    private String comment;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @ManyToOne(cascade = {DETACH,MERGE,REFRESH})
    private Card card;

     @ManyToOne(cascade = {DETACH,MERGE,REFRESH})
    private User user;


}
