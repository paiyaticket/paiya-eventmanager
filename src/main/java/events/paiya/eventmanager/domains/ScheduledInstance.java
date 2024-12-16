package events.paiya.eventmanager.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.*;
import java.util.UUID;

/**
 * Représente la programmation d'un évènement qui se déroule sur des jour différents.
 * Exemple, un championnat de foot avec plusieurs match; le championnat
 * et l'évènement et chaque match est une ScheduledInstance.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScheduledInstance {
    private String id = UUID.randomUUID().toString();
    private String title;
    private String description;
    private Instant startTime;
    private Instant endTime;
}
