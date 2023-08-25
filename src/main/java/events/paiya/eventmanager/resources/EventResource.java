package events.paiya.eventmanager.resources;

import events.paiya.eventmanager.domains.Adresse;
import events.paiya.eventmanager.domains.EventOrganizer;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.TimeZone;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EventResource{
    private String id;
    private String title;
    private String eventType;
    private String eventCategory;
    private List<String> tags;
    private String imageCover;
    private String resume;
    private String description;
    private LocalDateTime publicationDate;
    private Boolean visibility;
    private String eventPageLanguage;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private LocalTime startingHour;
    private LocalTime endingHour;
    private TimeZone timeZone;
    private Adresse adresse;
    private EventOrganizer eventOrganizer;

    // Audit properties
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Integer version;
}
