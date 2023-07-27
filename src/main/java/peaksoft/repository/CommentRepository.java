package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.models.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
