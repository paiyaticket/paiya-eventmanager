package events.paiya.eventmanager.services;

import events.paiya.eventmanager.domains.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    Event create(Event event);
    List<Event> findEventsByCreatedBy(String owner);
    Page<Event> findByVisibilityIsTrue(Pageable pageable);
    List<Event> findAllByVisibilityIsTrue();
    Event update(Event event);
    void deleteById(String eventId);

    List<Event> findEventsByStartingDateBetweenAndVisibilityIsTrue(LocalDate startingDate1, LocalDate startingDate2);
    List<Event> findEventsByTitleLikeIgnoreCaseAndVisibilityIsTrue(String title);
    List<Event> findEventsByTown(String townName);
}
