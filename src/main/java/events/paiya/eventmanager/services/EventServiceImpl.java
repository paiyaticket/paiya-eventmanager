package events.paiya.eventmanager.services;

import events.paiya.eventmanager.domains.Event;
import events.paiya.eventmanager.repositories.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event create(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> findEventsByCreatedBy(String owner) {
        return eventRepository.findEventsByCreatedBy(owner);
    }

    @Override
    public Page<Event> findByVisibilityIsTrue(Pageable pageable) {
        return eventRepository.findByVisibilityIsTrue(pageable);
    }

    @Override
    public List<Event> findAllByVisibilityIsTrue() {
        return eventRepository.findAllByVisibilityIsTrue();
    }

    @Override
    public Event update(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public void deleteById(String eventId) {
        eventRepository.deleteById(eventId);
    }

    @Override
    public List<Event> findEventsByStartingDateBetweenAndVisibilityIsTrue(LocalDate startingDate1, LocalDate startingDate2) {
        return eventRepository.findEventsByStartingDateBetweenAndVisibilityIsTrue(startingDate1, startingDate2);
    }

    @Override
    public List<Event> findEventsByTitleLikeIgnoreCaseAndVisibilityIsTrue(String title) {
        return eventRepository.findEventsByTitleLikeIgnoreCaseAndVisibilityIsTrue(title);
    }

    @Override
    public List<Event> findEventsByTown(String townName) {
        return eventRepository.findEventsByTown(townName);
    }
}
