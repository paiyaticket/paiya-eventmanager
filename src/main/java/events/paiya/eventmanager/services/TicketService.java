package events.paiya.eventmanager.services;

import events.paiya.eventmanager.domains.Ticket;
import events.paiya.eventmanager.repositories.TicketRepository;
import java.util.List;

public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository){
        this.ticketRepository = ticketRepository;
    }

    public Ticket save(Ticket ticket){
        return ticketRepository.save(ticket);
    }

    public Ticket findById(String id){
        return ticketRepository.findById(id).orElseThrow();
    }

    public List<Ticket> findByEventId(String eventId){
        return ticketRepository.findByEventId(eventId);
    }

    public void delete(String id){
        ticketRepository.deleteById(id);
    }
} 