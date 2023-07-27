package peaksoft.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.models.Column;

public interface ColumnRepository  extends JpaRepository<Column,Long> {

}
