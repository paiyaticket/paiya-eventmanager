package events.paiya.eventmanager.resources;

import lombok.*;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TicketResource {
    private String id;
    private String code;
    private String name;
    private Integer quantity;
    private Double price;
    private Boolean isTransactionFeesSupported;
    private LocalDateTime startDateOfSales;
    private LocalDateTime endDateOfSales;
    private Integer minimumTicketQuantityPerOrder;
    private Integer maximumTicketQuantityPerOrder;
    private String description;
    private String eventId;

    // Audit properties
    private String owner;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Integer version;
}
