package events.paiya.eventmanager.domains;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

/*
 * AGENDAPHASE
 * Represente an part, a specific moment in the planning of an event. 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {
    private String order;
    private Instant startTime;
    private Instant endTime;
    private String title;
    private String icon;
    private String description;
    private List<Speaker> spekers;
}
