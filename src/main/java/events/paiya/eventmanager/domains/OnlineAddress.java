package events.paiya.eventmanager.domains;

import events.paiya.eventmanager.enumeration.OnlinePlatform;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnlineAddress extends Adresse{
    private OnlinePlatform onlinePlatform;
    private String link;
}
