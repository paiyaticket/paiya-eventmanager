package events.paiya.eventmanager.domains;

import events.paiya.eventmanager.enumeration.EventReccurency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Scheduling {
    private EventReccurency eventReccurency;
    private List<ScheduledInstance> scheduledInstances;
}
