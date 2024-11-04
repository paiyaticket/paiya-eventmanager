package events.paiya.eventmanager.domains;
import events.paiya.eventmanager.enumeration.TicketType;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Ticket{
    private String id;
    private String code;
    private String label;
    private Integer quantity;
    private TicketType ticketType;
    private Double price;
    private Boolean transactionFeesSupported;
    // @JsonSerialize(using = LocalDateTimeSerializer.class)
    // @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private Instant startDateOfSales;
    // @JsonSerialize(using = LocalDateTimeSerializer.class)
    // @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private Instant endDateOfSales;
    @Builder.Default
    private Integer minimumTicketQuantityPerOrder = 1;
    @Builder.Default
    private Integer maximumTicketQuantityPerOrder = 3;
    private String details;
    private Boolean refundable;
    private String refundPolicy;
    private String eventId;


    // Audit properties
    private String owner;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @Version
    private Integer version;
}
