package events.paiya.eventmanager.domains;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.TimeZone;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Event{
    @Id
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
    private String timeZone;
    private Adresse adresse;
    private EventOrganizer eventOrganizer;

    // Audit properties
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @Version
    private Integer version;
}
