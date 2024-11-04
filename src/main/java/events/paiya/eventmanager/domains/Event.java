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
    private List<ImageCover> imageCovers;
    private String videoLink;
    private String summary;
    private String description;
    private Instant publicationDate;
    private Boolean published;

    // private LocalDate date;
    // private Instant date;
    private Instant startTime;
    private Instant endTime;
    private Scheduling scheduling;
    private String timeZone;
    private Integer timeZoneOffset;
    
    private PhysicalAddress physicalAddress;
    private OnlineAddress onlineAddress;
    private Langages eventPageLanguage;
    private EventOrganizer eventOrganizer;
    private List<Ticket> tickets;
    private List<CashAccount> cashAccounts;

    private PublishSettings publishSettings;

    private List<TimeSlot> agenda;
    private List<Question> faq;
    
    // Audit properties
    private String owner;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @Version
    private Integer version;

}
