package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.CardResponse;
import peaksoft.entity.Card;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card,Long> {

    @Query("select new peaksoft.dto.response.CardResponse(c.id,c.title) from Card c right join Column co on co.cards where co.isArchive = true")
    List<CardResponse> getAllCards();

    Optional<CardResponse> findCardById(Long id);
}
