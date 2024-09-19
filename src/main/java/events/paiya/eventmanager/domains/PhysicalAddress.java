package events.paiya.eventmanager.domains;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
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