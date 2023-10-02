package peaksoft.house.tasktrackerb9.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.house.tasktrackerb9.models.Estimation;

public interface EstimationRepository extends JpaRepository<Estimation,Long> {

}