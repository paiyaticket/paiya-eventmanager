package events.paiya.eventmanager.mappers;

import events.paiya.eventmanager.domains.Event;
import events.paiya.eventmanager.resources.EventResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEntity(EventResource resource);
    EventResource toResource(Event event);
}
