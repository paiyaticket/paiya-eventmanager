package events.paiya.eventmanager.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScheduledInstance {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
}
