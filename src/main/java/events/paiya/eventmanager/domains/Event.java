package events.paiya.eventmanager.domains;

import events.paiya.eventmanager.enumeration.Langages;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
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
    private String eventType;
    private String eventCategory;
    private List<String> tags;
    private String imageCover;
    private String resume;
    private String description;
    private LocalDateTime publicationDate;
    private Boolean visibility;
    private Langages eventPageLanguage;
    private LocalDateTime startingDateTime;
    private LocalDateTime endingDateTime;
    private String timeZone;
    private PhysicalAddress physicalAdresse;
    private OnlineAddress onlineAdresse;
    private EventOrganizer eventOrganizer;
    private List<Ticket> tickets;
    private List<CashAccount> cashAccounts;

    // Audit properties
    private String owner;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @Version
    private Integer version;
}
