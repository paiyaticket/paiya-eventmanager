package events.paiya.eventmanager.enumeration;

public enum EventType {
    SINGLE_EVENT("Sigle Event"),
    RECURRING_EVENT("Recurring Event");

    private String label;
    
    EventType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
