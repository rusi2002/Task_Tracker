package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.models.Estimation;

public interface EstimationRepository extends JpaRepository<Estimation,Long> {
}
