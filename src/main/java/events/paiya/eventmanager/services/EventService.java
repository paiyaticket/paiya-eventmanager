package events.paiya.eventmanager.services;

import events.paiya.eventmanager.domains.Event;
import events.paiya.eventmanager.repositories.EventRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.time.Instant;



@Component
@Slf4j
public class EventService{

    @Value("${paiya.event.popularity.treshold}")
    private float popularityTreshold;

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event findById(String id) {
        return eventRepository.findById(id).orElseThrow();
    }

    public Event findByIdAndPublishedIsTrue(String id) {
        return eventRepository.findByIdAndPublishedIsTrue(id).orElseThrow();
    }

    public List<Event> findMostRecentAddedEvents(int limit) {
        return eventRepository.findByPublishedIsTrueOrderByPublicationDateDesc(Limit.of(limit));
    }

     
    public Event create(Event event) {
        return eventRepository.save(event);
    }

     
    public List<Event>  findEventsByOwner(String owner) {
        return eventRepository.findEventsByOwner(owner);
    }

     
    public Page<Event> findByPublishedIsTrue(Pageable pageable) {
        return eventRepository.findByPublishedIsTrue(pageable);
    }

     
    public List<Event> findAllByPublishedIsTrue() {
        return eventRepository.findAllByPublishedIsTrue();
    }

     
    public Event update(Event event) {
        return eventRepository.save(event);
    }

     
    public Event publish(String eventId) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);

        if (eventOpt.isPresent()){
            Event event = eventOpt.get(); 
            event.setPublished(true);
            event.setPublicationDate(Instant.now());
            event.setPublishSettings(null);
            return eventRepository.save(event);
        } else {
            throw new NoSuchElementException();
        }
    }
     
    public void deleteAll() {
        eventRepository.deleteAll();
    }
     
    public void deleteById(String eventId) {
        eventRepository.deleteById(eventId);
    }

    public List<Event> findEventsByStartTimeBetweenAndPublishedIsTrue(Instant startingDate1, Instant startingDate2) {
        return eventRepository.findEventsByStartTimeBetweenAndPublishedIsTrue(startingDate1, startingDate2);
    }
     
    public List<Event> findEventsByTitleLikeIgnoreCaseAndPublishedIsTrue(String title) {
        return eventRepository.findEventsByTitleLikeIgnoreCaseAndPublishedIsTrue(title);
    }
     
    public List<Event> findEventsByTown(String townName) {
        return eventRepository.findEventsByPhysicalAddressTownLikeIgnoreCaseAndPublishedIsTrue(townName);
    }

    public Page<Event> findEventsByCountryAndTown(String country, String town, Pageable pageable) {
        return eventRepository.findEventsByCountryAndTown(country, town, pageable);
    }
  

    public Page<Event> findByPopularityTreshold(float popularityTreshold, Pageable pageable) {
        return eventRepository.findByPopularityIsGreaterThanEqualAndPublishedIsTrue(popularityTreshold, pageable);
    }

    public List<Event> findMostPopularEvents() {
        return eventRepository.findTop10ByPopularityIsGreaterThanAndPublishedIsTrue(popularityTreshold);

    }


}
