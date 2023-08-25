package events.paiya.eventmanager.domains;

import lombok.Data;

import java.util.List;

@Data
public class EventOrganizer {
    private String id;
    private String name;
    private String email;
    private List<String> phoneNumbers;
}
