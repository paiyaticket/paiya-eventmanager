package events.paiya.eventmanager.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

import events.paiya.eventmanager.domains.Ticket;

public interface TicketRepository extends MongoRepository<Ticket, String>{
    List<Ticket> findByEventId(String eventId);
}
