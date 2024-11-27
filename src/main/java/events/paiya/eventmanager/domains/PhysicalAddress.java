package events.paiya.eventmanager.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalAddress extends Adresse{
    private String location;
    private String locationIndication;
    private String street;
    private String streetNumber;
    private String town;
    private String postalCode;
    private String country;
    private String state;
    private String longitude;
    private String latitude;
}