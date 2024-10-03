package events.paiya.eventmanager.services;

import events.paiya.eventmanager.domains.Event;
import events.paiya.eventmanager.repositories.EventRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.time.*;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void givenEvent_thenCreate() {
        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(new Event());
        Event event = eventService.create(new Event());
        Assertions.assertNotNull(event);
        Mockito.verify(eventRepository, Mockito.times(1)).save(new Event());
    }

    @Test
    void givenOwnerId_thenFindEventsByOwner() {
        Mockito.when(eventRepository.findEventsByOwner(Mockito.anyString())).thenReturn(List.of(new Event()));
        List<Event> events = eventService.findEventsByOwner(UUID.randomUUID().toString());
        Assertions.assertFalse(events.isEmpty());
    }

    @Test
    void findByVisibilityIsTrue() {
        Pageable pageable = PageRequest.of(1, 1);
        Mockito.when(eventRepository.findByPublishedIsTrue(pageable)).thenReturn(new PageImpl<>(List.of(new Event()), pageable, 1));
        Page<Event> eventPage = eventService.findByPublishedIsTrue(pageable);
        Assertions.assertEquals(1, eventPage.getNumberOfElements());
        Assertions.assertEquals(1, eventPage.getContent().size());
    }

    @Test
    void findAllByVisibilityIsTrue() {
        Mockito.when(eventRepository.findAllByPublishedIsTrue()).thenReturn(List.of(new Event()));
        List<Event> events = eventService.findAllByPublishedIsTrue();
        Assertions.assertFalse(events.isEmpty());
    }

    @Test
    void givenId_whenExist_thenPublish() {
        String id = UUID.randomUUID().toString();
        Event eventSaved = Event.builder().timeZone(TimeZone.getDefault().getDisplayName())
                .publicationDate(LocalDateTime.now()).published(true).build();
        Mockito.when(eventRepository.findById(Mockito.anyString())).thenReturn(Optional.of(eventSaved));
        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(eventSaved);

        Event event = eventService.publish(id);

        Assertions.assertEquals(true, event.getPublished());
    }

    @Test
    void givenId_whenNotExist_thenThrowException() {
        String id = UUID.randomUUID().toString();
        Mockito.when(eventRepository.findById(Mockito.anyString())).thenThrow(new NoSuchElementException());

        Assertions.assertThrowsExactly(NoSuchElementException.class, () -> eventService.publish(id));
    }
    
    @Test
    void deleteById() {
        String id = UUID.randomUUID().toString();
        Mockito.doNothing().when(eventRepository).deleteById(Mockito.anyString());
        eventService.deleteById(id);
        Mockito.verify(eventRepository).deleteById(id);
    }

    @Test
    void findEventsByStartingDateBetweenAndVisibilityIsTrue() {
        Instant startingDate1 = Instant.parse("2023-07-01T12:00:00Z");
        Instant startingDate2 = Instant.parse("2023-07-07T12:00:00Z");
        Mockito.when(eventRepository.findEventsByStartTimeBetweenAndPublishedIsTrue(startingDate1, startingDate2)).thenReturn(List.of(new Event()));

        List<Event> events = eventService.findEventsByStartTimeBetweenAndPublishedIsTrue(startingDate1, startingDate2);

        Mockito.verify(eventRepository).findEventsByStartTimeBetweenAndPublishedIsTrue(startingDate1, startingDate2);
        Assert.notEmpty(events, () -> "Events array must not be empty");
    }

    @Test
    void findEventsByTitleLikeIgnoreCaseAndVisibilityIsTrue() {
        Mockito.when(eventRepository.findEventsByTitleLikeIgnoreCaseAndPublishedIsTrue(Mockito.anyString())).thenReturn(List.of(new Event()));

        List<Event> events = eventService.findEventsByTitleLikeIgnoreCaseAndPublishedIsTrue("");

        Mockito.verify(eventRepository).findEventsByTitleLikeIgnoreCaseAndPublishedIsTrue("");
        Assert.notEmpty(events, () -> "Events array must not be empty");
    }

    @Test
    void findEventsByTown() {
        String town = "test";
        Mockito.when(eventRepository.findEventsByPhysicalAddressTownLikeIgnoreCaseAndPublishedIsTrue(Mockito.anyString())).thenReturn(List.of(new Event()));

        List<Event> events = eventService.findEventsByTown(town);

        Mockito.verify(eventRepository).findEventsByPhysicalAddressTownLikeIgnoreCaseAndPublishedIsTrue("test");
        Assert.notEmpty(events, () -> "Events array must not be empty");
    }
}