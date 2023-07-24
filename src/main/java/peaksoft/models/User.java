package peaksoft.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import peaksoft.enums.Role;

import java.util.Collection;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "users_gen",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "users_gen",sequenceName = "users_seq",allocationSize = 1)
    private Long id;

    @jakarta.persistence.Column(name = "first_name")
    private String firstName;

    @jakarta.persistence.Column(name = "last_name")
    private String lastName;

    private String email;

    private String password;

    private String image;


    @ManyToMany(cascade = {MERGE,DETACH,REFRESH})
    private List<WorkSpace> workSpaces;

    @OneToMany(cascade = {ALL}, mappedBy = "user")
    private List<Favorite> favorites;

    @ManyToMany(cascade = {MERGE,DETACH,REFRESH}, mappedBy = "users")
    private List<Column> columns;

    @ManyToMany(cascade = {MERGE,DETACH,REFRESH}, mappedBy = "users")
    private List<Card> cards;

    @ManyToMany(cascade = {MERGE,DETACH,REFRESH}, mappedBy = "users")
    private List<Notification> notifications;

    @OneToMany(cascade = {ALL}, mappedBy = "user")
    private List<Comment> comments;

    @ManyToMany(cascade = {MERGE,DETACH,REFRESH}, mappedBy = "users")
    private List<Board> boards;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(cascade = {ALL}, mappedBy = "user")
    private List<UserWorkSpaceRole> roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
