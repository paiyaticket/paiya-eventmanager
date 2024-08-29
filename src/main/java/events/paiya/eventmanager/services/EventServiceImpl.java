package events.paiya.eventmanager.services;

import events.paiya.eventmanager.domains.Event;
import events.paiya.eventmanager.domains.Ticket;
import events.paiya.eventmanager.repositories.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Component
@Slf4j
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event findByIdAndVisibilityIsTrue(String id) {
        return eventRepository.findByIdAndVisibilityIsTrue(id).orElseThrow();
    }

    @Override
    public Event create(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event>  findEventsByOwner(String owner) {
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
    public Event update(String eventId, Event event) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isPresent()){
            Event oldEvent = eventOpt.get();
            if(!ObjectUtils.nullSafeEquals(oldEvent.getTitle(), event.getTitle())) oldEvent.setTitle(event.getTitle()) ;
            if(!ObjectUtils.nullSafeEquals(oldEvent.getEventType(), event.getEventType())) oldEvent.setEventType(event.getEventType());
            if(!ObjectUtils.nullSafeEquals(oldEvent.getEventCategory(), event.getEventCategory())) oldEvent.setEventCategory(event.getEventCategory());
            if(!ObjectUtils.nullSafeEquals(oldEvent.getTags(), event.getTags())) oldEvent.setTags(event.getTags());
            if(!ObjectUtils.nullSafeEquals(oldEvent.getImageCover(), event.getImageCover())) oldEvent.setImageCover(event.getImageCover());
            if(!ObjectUtils.nullSafeEquals(oldEvent.getResume(), event.getResume())) oldEvent.setResume(event.getResume());
            if(!ObjectUtils.nullSafeEquals(oldEvent.getDescription(), event.getDescription())) oldEvent.setDescription(event.getDescription());
            if(!ObjectUtils.nullSafeEquals(oldEvent.getEventPageLanguage(), event.getEventPageLanguage())) oldEvent.setEventPageLanguage(event.getEventPageLanguage());
            if(!ObjectUtils.nullSafeEquals(oldEvent.getStartingDate(), event.getStartingDate())) oldEvent.setStartingDate(event.getStartingDate());
            if(!ObjectUtils.nullSafeEquals(oldEvent.getEndingDate(), event.getEndingDate())) oldEvent.setEndingDate(event.getEndingDate());
            if(!ObjectUtils.nullSafeEquals(oldEvent.getStartingHour(), event.getStartingHour())) oldEvent.setStartingHour(event.getStartingHour());
            if(!ObjectUtils.nullSafeEquals(oldEvent.getEndingHour(), event.getEndingHour())) oldEvent.setEndingHour(event.getEndingHour());
            if(!ObjectUtils.nullSafeEquals(oldEvent.getTimeZone(), event.getTimeZone())) oldEvent.setTimeZone(event.getTimeZone());
            if(!ObjectUtils.nullSafeEquals(oldEvent.getPhysicalAdresse(), event.getPhysicalAdresse())) oldEvent.setPhysicalAdresse(event.getPhysicalAdresse());
            if(!ObjectUtils.nullSafeEquals(oldEvent.getOnlineAdresse(), event.getOnlineAdresse())) oldEvent.setOnlineAdresse(event.getOnlineAdresse());
            if(!ObjectUtils.nullSafeEquals(oldEvent.getEventOrganizer(), event.getEventOrganizer())) oldEvent.setEventOrganizer(event.getEventOrganizer());
            if(!ObjectUtils.nullSafeEquals(oldEvent.getCreatedBy(), event.getCreatedBy())) oldEvent.setCreatedBy(event.getCreatedBy());

            return eventRepository.save(oldEvent);
        }
        return eventRepository.insert(event);
    }

    @Override
    public Event publish(String eventId) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);

        if (eventOpt.isPresent()){
            Event event = eventOpt.get();
            event.setVisibility(true);
            event.setPublicationDate(LocalDateTime.now());
            return eventRepository.save(event);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void addTicketCategorie(String eventId, Ticket ticketCategorie) {
        eventRepository.addTicketCategorie(eventId, ticketCategorie);
    }

    @Override
    public void removeTicketCategorie(String eventId, String categorieCode) {
        eventRepository.removeTicketCategorie(eventId, categorieCode);
    }

    @Override
    public void updateTicketCategorieBy(String eventId, String categorieCode, Ticket ticketCategorie) {
        eventRepository.updateTicketCategorieBy(eventId, categorieCode, ticketCategorie);
    }

    @Override
    public void deleteAll() {
        eventRepository.deleteAll();
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
        return eventRepository.findEventsByPhysicalAdresseTownLikeIgnoreCaseAndVisibilityIsTrue(townName);
    }



}
