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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    void givenEvent_thenCreate() {
        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(new Event());
        Event event = eventService.create(new Event());
        Assertions.assertNotNull(event);
        Mockito.verify(eventRepository, Mockito.times(1)).save(new Event());
    }

    @Test
    void givenOwnerId_thenFindEventsByOwner() {
        Mockito.when(eventRepository.findEventsByCreatedBy(Mockito.anyString())).thenReturn(List.of(new Event()));
        List<Event> events = eventService.findEventsByOwner(UUID.randomUUID().toString());
        Assert.notEmpty(events, () -> "Events array must not be empty");
    }

    @Test
    void findByVisibilityIsTrue() {
        Pageable pageable = PageRequest.of(1, 1);
        Mockito.when(eventRepository.findByVisibilityIsTrue(pageable)).thenReturn(new PageImpl<>(List.of(new Event()), pageable, 1));
        Page<Event> eventPage = eventService.findByVisibilityIsTrue(pageable);
        Assertions.assertEquals(eventPage.getNumberOfElements(), 1);
        Assertions.assertEquals(eventPage.getContent().size(), 1);
    }

    @Test
    void findAllByVisibilityIsTrue() {
        Mockito.when(eventRepository.findAllByVisibilityIsTrue()).thenReturn(List.of(new Event()));
        List<Event> events = eventService.findAllByVisibilityIsTrue();
        Assert.notEmpty(events, () -> "Events array must not be empty");
    }

    @Test
    void givenId_whenExist_thenSave() {
        Mockito.when(eventRepository.existsById(Mockito.anyString())).thenReturn(true);
        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(new Event());
        Event event = eventService.update("", new Event());
        Assert.notNull(event, () -> "Must not return null");
        Mockito.verify(eventRepository, Mockito.times(1)).save(new Event());
    }

    @Test
    void givenId_whenNotExist_thenInsert() {
        Mockito.when(eventRepository.existsById(Mockito.anyString())).thenReturn(false);
        Mockito.when(eventRepository.insert(Mockito.any(Event.class))).thenReturn(new Event());
        Event event = eventService.update("", new Event());
        Assert.notNull(event, () -> "Must not return null");
        Mockito.verify(eventRepository, Mockito.times(1)).insert(new Event());
    }

    @Test
    void givenId_whenExist_thenPublish() {
        String id = UUID.randomUUID().toString();
        Event eventSaved = Event.builder().timeZone(TimeZone.getDefault().getDisplayName())
                .publicationDate(LocalDateTime.now()).visibility(true).build();
        Mockito.when(eventRepository.findById(Mockito.anyString())).thenReturn(Optional.of(eventSaved));
        Mockito.when(eventRepository.save(Mockito.any(Event.class))).thenReturn(eventSaved);

        Event event = eventService.publish(id);

        Assertions.assertEquals(true, event.getVisibility());
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
        LocalDate startingDate1 = LocalDate.of(2023, 7, 1);
        LocalDate startingDate2 = LocalDate.of(2023, 7, 7);
        Mockito.when(eventRepository.findEventsByStartingDateBetweenAndVisibilityIsTrue(startingDate1, startingDate2)).thenReturn(List.of(new Event()));

        List<Event> events = eventService.findEventsByStartingDateBetweenAndVisibilityIsTrue(startingDate1, startingDate2);

        Mockito.verify(eventRepository).findEventsByStartingDateBetweenAndVisibilityIsTrue(startingDate1, startingDate2);
        Assert.notEmpty(events, () -> "Events array must not be empty");
    }

    @Test
    void findEventsByTitleLikeIgnoreCaseAndVisibilityIsTrue() {
        Mockito.when(eventRepository.findEventsByTitleLikeIgnoreCaseAndVisibilityIsTrue(Mockito.anyString())).thenReturn(List.of(new Event()));

        List<Event> events = eventService.findEventsByTitleLikeIgnoreCaseAndVisibilityIsTrue("");

        Mockito.verify(eventRepository).findEventsByTitleLikeIgnoreCaseAndVisibilityIsTrue("");
        Assert.notEmpty(events, () -> "Events array must not be empty");
    }

    @Test
    void findEventsByTown() {
        String town = "test";
        Mockito.when(eventRepository.findEventsByTown(Mockito.anyString())).thenReturn(List.of(new Event()));

        List<Event> events = eventService.findEventsByTown(town);

        Mockito.verify(eventRepository).findEventsByTown("/.*test.*/");
        Assert.notEmpty(events, () -> "Events array must not be empty");
    }
}