package events.paiya.eventmanager.domains;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageCover {
    private String source;
    @Builder.Default
    private boolean byDefault = false;
    private String name;
    private String alt;

}
