package peaksoft.models;

import jakarta.persistence.Column;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "attachments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment {

    @Id
    @GeneratedValue(generator = "attachment_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "attachment_gen", sequenceName = "attachment_seq", allocationSize = 1)
    private Long id;

    @Column(name = "document_link")
    private String documentLink;

    @Column(name = "created_at")
    private ZonedDateTime createdAt;

    @ManyToOne(cascade = {DETACH,MERGE,REFRESH})
    private Card card;

}
