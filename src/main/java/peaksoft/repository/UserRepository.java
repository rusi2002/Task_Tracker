package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> getUserByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findUserByEmail(String email);

}