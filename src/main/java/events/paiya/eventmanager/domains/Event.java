package events.paiya.eventmanager.domains;

import events.paiya.eventmanager.enumeration.EventType;
import events.paiya.eventmanager.enumeration.Langages;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.*;
import java.util.List;

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
    private EventType eventType;
    private String eventCategory;
    private List<String> tags;
    private String imageCover;
    private String summary;
    private String description;
    private LocalDateTime publicationDate;
    private Boolean published;

    private LocalDate date;
    private LocalTime startingTime;
    private LocalTime endingTime;
    private Scheduling scheduling;
    private String timeZone;
    
    private PhysicalAddress physicalAdresse;
    private OnlineAddress onlineAdresse;
    private Langages eventPageLanguage;
    private EventOrganizer eventOrganizer;
    private List<Ticket> tickets;
    private List<CashAccount> cashAccounts;

    private PublishSettings publishSettings;
    
    // Audit properties
    private String owner;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @Version
    private Integer version;

}
