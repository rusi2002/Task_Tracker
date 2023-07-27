package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.dto.response.CardResponse;
import peaksoft.models.Card;

import java.util.Optional;

public interface CardRepository extends JpaRepository<Card,Long> {

//    @Query("select new peaksoft.dto.response.CardResponse(c.id,c.title) from Card c right join Column co on co.cards where co.isArchive = true")
//    List<CardResponse> getAllCards();

    Optional<CardResponse> findCardById(Long id);
}
