package events.paiya.eventmanager.errors;

import events.paiya.eventmanager.resources.ApiErrorResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;

@RestControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler({DataFormatException.class, DateTimeParseException.class, IllegalArgumentException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResource> handleDateFormatException(Exception ex){
        ApiErrorResource apiErrorResource = new ApiErrorResource("paiya.eventmanager.internal.error.message",
                ex.getMessage(), HttpStatus.BAD_REQUEST, null);
        return ResponseEntity.ofNullable(apiErrorResource);
    }

    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorResource> handleNotFound(Exception ex){
        ApiErrorResource apiErrorResource = new ApiErrorResource("paiya.eventmanager.internal.error.message",
                ex.getMessage(), HttpStatus.NOT_FOUND, null);
        return ResponseEntity.ofNullable(apiErrorResource);
    }
}
