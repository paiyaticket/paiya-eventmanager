package events.paiya.eventmanager.controllers;

import events.paiya.eventmanager.domains.Ticket;
import events.paiya.eventmanager.mappers.TicketMapper;
import events.paiya.eventmanager.resources.TicketResource;
import events.paiya.eventmanager.services.TicketService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    public TicketController(TicketService ticketService, TicketMapper ticketMapper){
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
    }

    @PostMapping
    public ResponseEntity<TicketResource> create(@RequestBody TicketResource ticketResource){
        Ticket ticket = ticketMapper.toEntity(ticketResource);
        ticket = ticketService.save(ticket);
        ticketResource = ticketMapper.toResource(ticket);
        URI uri = URI.create("");
        return ResponseEntity.created(uri).body(ticketResource);
    }

    @GetMapping()
    public ResponseEntity<List<TicketResource>> findByEvent(@RequestParam(name = "eventId") String eventId){
        List<Ticket> tickets = ticketService.findByEventId(eventId);
        List<TicketResource> ticketResources = ticketMapper.toResourceList(tickets);
        return ResponseEntity.ok().body(ticketResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResource> findById(@PathVariable(name = "id") String id){
        Ticket ticket = ticketService.findById(id);
        TicketResource ticketResource = ticketMapper.toResource(ticket);
        return ResponseEntity.ok().body(ticketResource);
    } 

    @PatchMapping("/{id}")
    public ResponseEntity<TicketResource> update(@PathVariable(name = "id") String id, @RequestBody TicketResource ticketResource){
        Ticket ticket = ticketService.findById(id);
        ticketMapper.update(ticketResource, ticket);
        ticket = ticketService.save(ticket);
        ticketResource = ticketMapper.toResource(ticket);
        return ResponseEntity.ok().body(ticketResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") String id){
        ticketService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
