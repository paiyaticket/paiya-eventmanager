package events.paiya.eventmanager.domains;

import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * AGENDAPHASE
 * Represente an part, a specific moment in the planning of an event. 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgendaPhase {
    private String order;
    private LocalTime startTime;
    private LocalTime endTime;
    private String title;
    private String icon;
    private String description;
    private List<Speaker> spekers;
}
