package events.paiya.eventmanager.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import events.paiya.eventmanager.domains.Ticket;
import events.paiya.eventmanager.mappers.TicketMapper;
import events.paiya.eventmanager.resources.TicketResource;
import events.paiya.eventmanager.services.TicketService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;;


@RestController
@RequestMapping("/v1/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final TicketMapper ticketMapper;

    public TicketController(TicketService ticketService, TicketMapper ticketMapper){
        this.ticketService = ticketService;
        this.ticketMapper = ticketMapper;
    }

    @PostMapping()
    public ResponseEntity<TicketResource> create(@RequestBody TicketResource ticketResource){
        Ticket ticket = ticketMapper.toEntity(ticketResource);
        ticket = ticketService.save(ticket);
        ticketResource = ticketMapper.toResource(ticket);
        return ResponseEntity.ok().body(ticketResource);
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

    @PatchMapping()
    public ResponseEntity<TicketResource> update(@RequestBody TicketResource ticketResource){
        Ticket ticket = ticketMapper.toEntity(ticketResource);
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
