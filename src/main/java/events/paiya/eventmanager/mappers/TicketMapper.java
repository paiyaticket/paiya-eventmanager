package events.paiya.eventmanager.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import events.paiya.eventmanager.domains.Ticket;
import events.paiya.eventmanager.resources.TicketResource;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    Ticket toEntity(TicketResource resource);
    TicketResource toResource(Ticket entity);

    List<Ticket> toEntityList(List<TicketResource> resource);
    List<TicketResource> toResourceList(List<Ticket> event);
}
