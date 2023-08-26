package events.paiya.eventmanager.errors;

import events.paiya.eventmanager.resources.ApiErrorResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;

@ControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler({DataFormatException.class, DateTimeParseException.class})
    public ResponseEntity<Object> handleDateFormatException(Exception ex){
        ApiErrorResource apiErrorResource = new ApiErrorResource("paiya.eventmanager.internal.error.message",
                ex.getMessage(), HttpStatus.BAD_REQUEST, null);
        return new ResponseEntity<>(apiErrorResource, apiErrorResource.getStatus());
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Object> handleNotFound(Exception ex){
        ApiErrorResource apiErrorResource = new ApiErrorResource("paiya.eventmanager.internal.error.message",
                ex.getMessage(), HttpStatus.NOT_FOUND, null);
        return new ResponseEntity<>(apiErrorResource, apiErrorResource.getStatus());
    }

    @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<Object> handleBadParameter(Exception ex){
        ApiErrorResource apiErrorResource = new ApiErrorResource("paiya.eventmanager.internal.error.message",
                ex.getMessage(), HttpStatus.BAD_REQUEST, null);
        return new ResponseEntity<>(apiErrorResource, apiErrorResource.getStatus());
    }
}
