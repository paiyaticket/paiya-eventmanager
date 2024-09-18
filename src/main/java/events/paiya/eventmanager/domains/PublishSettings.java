package events.paiya.eventmanager.domains;

import events.paiya.eventmanager.enumeration.EventVisibility;
import events.paiya.eventmanager.enumeration.PublishMoment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublishSettings {
    private EventVisibility eventVisibility;
    @Builder.Default
    private PublishMoment whenToPublish = PublishMoment.NOW;
    private LocalDate date;
    private LocalTime time;
    private String timezone;
}
