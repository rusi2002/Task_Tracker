package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.models.CheckList;

public interface CheckListRepository extends JpaRepository<CheckList,Long> {
}
