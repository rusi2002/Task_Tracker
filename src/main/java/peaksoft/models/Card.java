package peaksoft.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @Id
    @GeneratedValue(generator = "card_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "card_gen",sequenceName = "card_seq",allocationSize = 1)
    private Long id;

    private String title;

    private String description;

    @jakarta.persistence.Column(name = "is_archive")
    private Boolean isArchive;

    @ManyToMany(cascade ={DETACH,MERGE,REFRESH})
    private List<User>users;

    @OneToMany(cascade = {ALL},mappedBy = "card")
    private List<CheckList>checkLists;

    @OneToOne(cascade = {ALL},mappedBy = "card")
    private Estimation estimation;

    @OneToMany(cascade = {ALL},mappedBy = "card")
    private List<Comment>comments;

    @ManyToOne(cascade = {DETACH,MERGE,REFRESH})
    private Column column;

    @OneToMany(cascade = {ALL},mappedBy = "card")
    private List<Attachment>attachments;

    @ManyToMany(cascade = {DETACH,MERGE,REFRESH},mappedBy = "cards")
    private List<Label>labels;

    @OneToMany(cascade = {DETACH,MERGE,REFRESH},mappedBy = "card")
    private List<Notification>notifications;
}
