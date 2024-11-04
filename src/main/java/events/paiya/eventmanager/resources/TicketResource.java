package events.paiya.eventmanager.resources;

import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

import events.paiya.eventmanager.enumeration.TicketType;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TicketResource {
    private String id;
    private String code;
    private String label;
    private Integer quantity;
    private TicketType ticketType;
    private Double price;
    private Boolean transactionFeesSupported;
    private Instant startDateOfSales;
    private Instant endDateOfSales;
    private Integer minimumTicketQuantityPerOrder;
    private Integer maximumTicketQuantityPerOrder;
    private String details;
    private Boolean refundable;
    private String refundPolicy;
    private String eventId;

    // Audit properties
    private String owner;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Integer version;
}
