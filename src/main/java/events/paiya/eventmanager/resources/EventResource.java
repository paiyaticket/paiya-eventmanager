package events.paiya.eventmanager.resources;

import events.paiya.eventmanager.domains.EventOrganizer;
import events.paiya.eventmanager.domains.OnlineAdresse;
import events.paiya.eventmanager.domains.PhysicalAdresse;
import events.paiya.eventmanager.enumeration.Langages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EventResource{
    private String id;
    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Must contain no more than 100 caracters")
    private String title;

    @Size(max = 30, message = "Must contain no more than 50 caracters")
    private String eventType;
    @Size(max = 30, message = "Must contain no more than 50 caracters")
    private String eventCategory;
    @Builder.Default
    private List<String> tags = new ArrayList<>();
    private String imageCover;
    @Size(max = 150, message = "Must contain no more than 100 caracters")
    private String resume;
    @Size(max = 3000, message = "Must contain no more than 100 caracters")
    private String description;
    private LocalDateTime publicationDate;
    private Boolean visibility;
    private Langages eventPageLanguage;
    private LocalDate startingDate;
    private LocalDate endingDate;
    private LocalTime startingHour;
    private LocalTime endingHour;
    private String timeZone;
    private PhysicalAdresse physicalAdresse;
    private OnlineAdresse onlineAdresse;
    private EventOrganizer eventOrganizer;

    // Audit properties
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Integer version;
}
