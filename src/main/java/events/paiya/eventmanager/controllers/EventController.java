package events.paiya.eventmanager.controllers;

import events.paiya.eventmanager.domains.Event;
import events.paiya.eventmanager.mappers.EventMapper;
import events.paiya.eventmanager.resources.EventResource;
import events.paiya.eventmanager.services.EventService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.time.Instant;
import java.time.LocalDate;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/v1/events")
public class EventController {
    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @PostMapping
    public ResponseEntity<EventResource> create(@RequestBody @Valid EventResource resource, HttpServletRequest request) throws URISyntaxException {
        Event event = eventMapper.toEntity(resource);
        event = eventService.create(event);
        URI uri = new URI(request.getContextPath()+"/"+request.getRequestURI()+"/"+event.getId());
        return ResponseEntity.created(uri).body(eventMapper.toResource(event));
    }

    @GetMapping("{id}")
    public ResponseEntity<EventResource> findById(@PathVariable(name = "id") String id){
        Event event = eventService.findById(id);
        return ResponseEntity.ok(eventMapper.toResource(event));
    }

    @GetMapping("owned-by")
    public ResponseEntity<List<EventResource>> findEventsByOwner(@RequestParam(name = "owner") String ownerId){
        List<Event> events = eventService.findEventsByOwner(ownerId);
        return ResponseEntity.ok(eventMapper.toResourceList(events));
    }

    @GetMapping("date-between")
    public ResponseEntity<List<EventResource>> findEventsByStartingDateBetweenAndVisibilityIsTrue(@RequestParam(name = "minDate") String minDate, @RequestParam(name = "maxDate") String maxDate){
        Instant minD = Instant.parse(minDate);
        Instant maxD = Instant.parse(maxDate);
        List<Event> events = eventService.findEventsByStartTimeBetweenAndPublishedIsTrue(minD, maxD);
        return ResponseEntity.ok(eventMapper.toResourceList(events));
    }

    @GetMapping("title-like")
    public ResponseEntity<List<EventResource>> findEventsByTitleLikeIgnoreCaseAndVisibilityIsTrue(@RequestParam(name = "title") String title){
        List<Event> events = eventService.findEventsByTitleLikeIgnoreCaseAndPublishedIsTrue(title);
        return ResponseEntity.ok(eventMapper.toResourceList(events));
    }

    @GetMapping("town-like")
    public ResponseEntity<List<EventResource>> findEventsByTown(@RequestParam(name = "town") String town){
        List<Event> events = eventService.findEventsByTown(town);
        return ResponseEntity.ok(eventMapper.toResourceList(events));
    }

    @GetMapping("paginated")
    public ResponseEntity<List<EventResource>> findByVisibilityIsTrue(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventPage = eventService.findByPublishedIsTrue(pageable);
        List<EventResource> eventResourceList = eventMapper.toResourceList(eventPage.stream().toList());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Total-Elements", String.valueOf(eventPage.getTotalElements()));
        httpHeaders.add("Total-Pages", String.valueOf(eventPage.getTotalPages()));
        return ResponseEntity.ok().headers(httpHeaders).body(eventResourceList);
    }

    @GetMapping
    public ResponseEntity<List<EventResource>> findAllByVisibilityIsTrue(){
        return ResponseEntity.ok(eventMapper.toResourceList(eventService.findAllByPublishedIsTrue()));
    }

    @PatchMapping("{id}")
    public ResponseEntity<EventResource> update(@PathVariable String id, @RequestBody EventResource eventResource){
        Event event = eventService.findById(id);
        eventMapper.update(eventResource, event);
        event = eventService.update(event);
        return ResponseEntity.ok(eventMapper.toResource(event));
    }

    @PatchMapping("publish/{id}")
    public ResponseEntity<EventResource> publish(@PathVariable(name = "id") String eventId){
        Event event = eventService.publish(eventId);
        return ResponseEntity.ok(eventMapper.toResource(event));
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        this.eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
