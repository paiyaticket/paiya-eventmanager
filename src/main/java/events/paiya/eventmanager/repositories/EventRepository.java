package events.paiya.eventmanager.repositories;

import events.paiya.eventmanager.domains.Event;
import events.paiya.eventmanager.domains.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Component
public interface EventRepository extends MongoRepository<Event, String> {
    Optional<Event> findByIdAndVisibilityIsTrue(String id);
    List<Event> findEventsByOwner(String owner);
    List<Event> findAllByVisibilityIsTrue();
    Page<Event> findByVisibilityIsTrue(Pageable pageable);
    List<Event> findEventsByStartingDateTimeBetweenAndVisibilityIsTrue(LocalDateTime startingDate1, LocalDateTime startingDate2);
    List<Event> findEventsByTitleLikeIgnoreCaseAndVisibilityIsTrue(String title);

    //@Query("{'physicalAdresse.town': {$regex : ?0, $options : 'i'}, 'visibility' :  true}")
    // List<Event> findEventsByTown(String townNameRegex);
    List<Event> findEventsByPhysicalAdresseTownLikeIgnoreCaseAndVisibilityIsTrue(String town);

    @Query("{'_id': ?0}")
    @Update("{'$push' : {'ticketCategories' : ?1} }")
    void addTicketCategorie(String eventId, Ticket ticketCategorie);

    @Query("{'_id': ?0, 'ticketCategories.categorieCode': ?1}")
    @Update("{'$set' : {'ticketCategories.$' : ?2} }")
    void updateTicketCategorieBy(String eventId, String categorieCode, Ticket ticketCategorie);

    @Query("{'_id': ?0}")
    @Update("{'$pull' : {'ticketCategories' : {categorieCode : ?1}} }")
    void removeTicketCategorie(String eventId, String categorieCode);

}
