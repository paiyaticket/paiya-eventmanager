package events.paiya.eventmanager.resources;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter @Setter
public class ApiErrorResource {
    private String code;
    private String message;
    private HttpStatus status;
    private Map<String, String> errors;

    public ApiErrorResource(String code, String message, HttpStatus status, Map<String, String> errors) {
        this.code = code;
        this.message = message;
        this.status = status;
        this.errors = errors;
    }
}
