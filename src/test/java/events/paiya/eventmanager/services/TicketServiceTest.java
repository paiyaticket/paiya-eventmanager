package events.paiya.eventmanager.services;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import events.paiya.eventmanager.domains.Ticket;
import events.paiya.eventmanager.repositories.TicketRepository;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    final String ID = "64ef8526c36bf3454a04cf92";
    
    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    @DisplayName("#save() method should create a new ticket")
    void givenTicket_thenCreate(){
        Ticket ticket = Ticket.builder().label("VIP").build();
        Mockito.when(ticketRepository.save(Mockito.any(Ticket.class))).thenReturn(ticket);

        ticketService.save(ticket);

        Mockito.verify(ticketRepository, atLeastOnce()).save(ticket);
    }

    @Test
    @DisplayName("#findById() should retrieve a ticket basing on an ID")
    void givenId_whenIdExist_thenReturnTicket(){
        Ticket ticket = Ticket.builder().id(ID).build();
        Mockito.when(ticketRepository.findById(ID)).thenReturn(Optional.of(ticket));

        Ticket result = ticketService.findById(ID);

        Mockito.verify(ticketRepository, atLeastOnce()).findById(ID);
        Assertions.assertEquals(ticket, result);
    }

    @Test
    @DisplayName("#findById() should throw a NoSuchElementException if no ID match")
    void givenId_whenIdNotExist_thenThrowNoSuchElementException(){
        
        Mockito.when(ticketRepository.findById(ID)).thenThrow(new NoSuchElementException());

        Assertions.assertThrows(NoSuchElementException.class, () -> ticketService.findById(ID));
        Mockito.verify(ticketRepository, atLeastOnce()).findById(ID);
    }

    @Test
    @DisplayName("#findByEventId() should throw a NoSuchElementException if no ID match")
    void givenId_whenEventIdExist_thenReturnTickets(){
        List<Ticket> tickets = List.of(Ticket.builder().id(ID).build());
        Mockito.when(ticketRepository.findByEventId(ID)).thenReturn(tickets);

        List<Ticket> result = ticketService.findByEventId(ID);

        Mockito.verify(ticketRepository, atLeastOnce()).findByEventId(ID);
        Assertions.assertEquals(tickets, result);
    }

    @Test
    @DisplayName("#delete() should delete a ticket basing on it ID")
    void givenId_thenDelete(){
        doNothing().when(ticketRepository).deleteById(ID);

        ticketService.delete(ID);

        Mockito.verify(ticketRepository, atLeastOnce()).deleteById(ID);
    }
}
