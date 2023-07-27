package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.models.Label;

public interface LabelRepository extends JpaRepository<Label,Long> {
}
