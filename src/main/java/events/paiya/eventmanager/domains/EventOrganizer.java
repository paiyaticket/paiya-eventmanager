package events.paiya.eventmanager.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventOrganizer {
    private String id;
    private String name;
    private String email;
    private List<String> phoneNumbers;
}
