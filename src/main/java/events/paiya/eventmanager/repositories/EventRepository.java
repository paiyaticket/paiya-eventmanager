package events.paiya.eventmanager.repositories;

import events.paiya.eventmanager.domains.Event;
import events.paiya.eventmanager.domains.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Component;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;
import java.time.Instant;
import java.time.LocalDate;


@Component
public interface EventRepository extends MongoRepository<Event, String> {

    Optional<Event> findByIdAndPublishedIsTrue(String id);

    List<Event> findEventsByOwner(String owner);

    List<Event> findAllByPublishedIsTrue();

    Page<Event> findByPublishedIsTrue(Pageable pageable);

    List<Event> findEventsByTitleLikeIgnoreCaseAndPublishedIsTrue(String title);

    List<Event> findEventsByPhysicalAddressTownLikeIgnoreCaseAndPublishedIsTrue(String town);

    List<Event> findEventsByStartTimeBetweenAndPublishedIsTrue(Instant date1, Instant date2);

    List<Event> findEventsByEndTimeBetweenAndPublishedIsTrue(Instant date1, Instant date2);

    List<Event> findEventsByPublicationDateBetweenAndPublishedIsTrue(LocalDate date1, LocalDate date2);

    List<Event> findByPopularityIsGreaterThanAndPublishedIsTrue(float popularity);

    @Query("{'physicalAddress.country' : ?0, 'physicalAddress.town': ?1, 'published' : true}")
    List<Event> findEventsByCountryAndTown(String country, String town);
 
    
    List<Event> findEventsByPhysicalAddressNear(Point point, Distance distance);

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
