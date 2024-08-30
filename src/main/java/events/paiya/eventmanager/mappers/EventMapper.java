package events.paiya.eventmanager.mappers;

import events.paiya.eventmanager.domains.Event;
import events.paiya.eventmanager.resources.EventResource;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    Event toEntity(EventResource resource);
    EventResource toResource(Event event);

    List<Event> toEntityList(List<EventResource> resource);
    List<EventResource> toResourceList(List<Event> event);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(EventResource resource, @MappingTarget Event entity);
}
