package events.paiya.eventmanager.domains;

import events.paiya.eventmanager.enumeration.OnlinePlatform;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OnlineAdresse extends Adresse{
    private OnlinePlatform onlinePlatform;
    private String link;
}
