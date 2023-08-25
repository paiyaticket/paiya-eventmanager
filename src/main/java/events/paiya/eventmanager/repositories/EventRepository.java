package events.paiya.eventmanager.repositories;

import events.paiya.eventmanager.domains.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findEventsByCreatedBy(String owner);
    List<Event> findAllByVisibilityIsTrue();
    Page<Event> findByVisibilityIsTrue(Pageable pageable);
    List<Event> findEventsByStartingDateBetweenAndVisibilityIsTrue(LocalDate startingDate1, LocalDate startingDate2);
    List<Event> findEventsByTitleLikeIgnoreCaseAndVisibilityIsTrue(String title);
    @Query("{'adresse.town': ?0, 'visibility' :  true}")
    List<Event> findEventsByTown(String townName);
}
