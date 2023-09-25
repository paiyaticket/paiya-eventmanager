package events.paiya.eventmanager.services;

import events.paiya.eventmanager.domains.Event;
import events.paiya.eventmanager.domains.TicketCategorie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface EventService {
    Event findByIdAndVisibilityIsTrue(String id);
    Event create(Event event);
    List<Event> findEventsByOwner(String owner);
    Page<Event> findByVisibilityIsTrue(Pageable pageable);
    List<Event> findAllByVisibilityIsTrue();
    Event update(String eventId, Event event);
    void deleteById(String eventId);

    List<Event> findEventsByStartingDateBetweenAndVisibilityIsTrue(LocalDate startingDate1, LocalDate startingDate2);
    List<Event> findEventsByTitleLikeIgnoreCaseAndVisibilityIsTrue(String title);
    List<Event> findEventsByTown(String townName);
    Event publish(String eventId);

    void addTicketCategorie(String eventId, TicketCategorie ticketCategorie);
    void removeTicketCategorie(String eventId, String categorieCode);
    void updateTicketCategorieBy(String eventId, String categorieCode, TicketCategorie ticketCategorie);

    void deleteAll();
}
