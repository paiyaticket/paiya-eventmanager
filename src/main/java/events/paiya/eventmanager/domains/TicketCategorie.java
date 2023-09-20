package events.paiya.eventmanager.domains;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TicketCategorie {
    private String categorieCode;
    private String categorieName;
    private Integer quantity;
    private Double price;
    private Boolean isTransactionFeesSupported;
    private LocalDateTime startDateOfSales;
    private LocalDateTime endDateOfSales;
    private Integer minimumTicketQuantityPerOrder;
    private Integer maximumTicketQuantityPerOrder;
    private String description;
    private String string;
}
