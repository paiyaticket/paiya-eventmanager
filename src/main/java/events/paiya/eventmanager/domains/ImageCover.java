package events.paiya.eventmanager.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageCover {
    private String source;
    @Builder.Default
    private boolean byDefault = false;
    private String name;
    private String alt;

}
