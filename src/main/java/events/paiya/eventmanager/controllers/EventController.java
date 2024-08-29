package events.paiya.eventmanager.controllers;

import events.paiya.eventmanager.domains.Event;
import events.paiya.eventmanager.domains.TicketCategory;
import events.paiya.eventmanager.mappers.EventMapper;
import events.paiya.eventmanager.resources.EventResource;
import events.paiya.eventmanager.services.EventServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/events")
@Slf4j
public class EventController {
    private final EventServiceImpl eventService;
    private final EventMapper eventMapper;

    public EventController(EventServiceImpl eventService, EventMapper eventMapper) {
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

    @GetMapping("owned-by")
    public ResponseEntity<List<EventResource>> findEventsByOwner(@RequestParam(name = "ownerId") String ownerId){
        List<Event> events = eventService.findEventsByOwner(ownerId);
        return ResponseEntity.ok(eventMapper.toResourceList(events));
    }

    @GetMapping("date-between")
    public ResponseEntity<List<EventResource>> findEventsByStartingDateBetweenAndVisibilityIsTrue(@RequestParam(name = "minDate") String minDate, @RequestParam(name = "maxDate") String maxDate){
        LocalDate minD = LocalDate.parse(minDate);
        LocalDate maxD = LocalDate.parse(maxDate);
        List<Event> events = eventService.findEventsByStartingDateBetweenAndVisibilityIsTrue(minD, maxD);
        return ResponseEntity.ok(eventMapper.toResourceList(events));
    }

    @GetMapping("title-like")
    public ResponseEntity<List<EventResource>> findEventsByTitleLikeIgnoreCaseAndVisibilityIsTrue(@RequestParam(name = "title") String title){
        List<Event> events = eventService.findEventsByTitleLikeIgnoreCaseAndVisibilityIsTrue(title);
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
        Page<Event> eventPage = eventService.findByVisibilityIsTrue(pageable);
        List<EventResource> eventResourceList = eventMapper.toResourceList(eventPage.stream().toList());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Total-Elements", String.valueOf(eventPage.getTotalElements()));
        httpHeaders.add("Total-Pages", String.valueOf(eventPage.getTotalPages()));
        return ResponseEntity.ok().headers(httpHeaders).body(eventResourceList);
    }

    @GetMapping
    public ResponseEntity<List<EventResource>> findAllByVisibilityIsTrue(){
        return ResponseEntity.ok(eventMapper.toResourceList(eventService.findAllByVisibilityIsTrue()));
    }

    @PutMapping("{id}")
    public ResponseEntity<EventResource> update(@PathVariable String id, @RequestBody EventResource eventResource){
        Event event = eventMapper.toEntity(eventResource);
        event = eventService.update(id, event);
        return ResponseEntity.ok(eventMapper.toResource(event));
    }

    @PutMapping("publish/{id}")
    public ResponseEntity<EventResource> publish(@PathVariable(name = "id") String eventId){
        Event event = eventService.publish(eventId);
        return ResponseEntity.ok(eventMapper.toResource(event));
    }

    @PutMapping("{id}/ticket-categorie/add")
    public ResponseEntity<EventResource> addTicketCategorie(@PathVariable(name = "id") String eventId,
                                                            @RequestBody @NotNull TicketCategory ticketCategorie) {
        if (ticketCategorie.getCode() == null) ticketCategorie.setCode(eventId + ".CAT." + UUID.randomUUID());
        eventService.addTicketCategorie(eventId, ticketCategorie);
        Event event = eventService.findByIdAndVisibilityIsTrue(eventId);
        return ResponseEntity.ok(eventMapper.toResource(event));
    }

    @PutMapping("{id}/ticket-categorie/update/{categorieCode}")
    public ResponseEntity<EventResource> updateTicketCategorie(@PathVariable(name = "id") String eventId,
                                                               @PathVariable(name = "categorieCode") String categorieCode,
                                                               @RequestBody @NotNull TicketCategory ticketCategorie) {
        eventService.updateTicketCategorieBy(eventId, categorieCode, ticketCategorie);
        Event event = eventService.findByIdAndVisibilityIsTrue(eventId);
        return ResponseEntity.ok(eventMapper.toResource(event));
    }

    @PutMapping("{id}/ticket-categorie/remove/{categorieCode}")
    public ResponseEntity<EventResource> removeTicketCategorie(@PathVariable(name = "id") String eventId,
                                                               @PathVariable(name = "categorieCode") String categorieCode) {
        eventService.removeTicketCategorie(eventId, categorieCode);
        Event event = eventService.findByIdAndVisibilityIsTrue(eventId);
        return ResponseEntity.ok(eventMapper.toResource(event));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        this.eventService.deleteById(id);
        return ResponseEntity.noContent().build();
    }



}
