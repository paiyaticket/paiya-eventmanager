package events.paiya.eventmanager.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * SPEAKER
 * A person who must to speak at a part of an event.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Speaker {
    private String completeName;
    private String photo;
    private String description;
    private String twitter;
    private String linkedin;
    private String instagram;
    private String facebook;
}
