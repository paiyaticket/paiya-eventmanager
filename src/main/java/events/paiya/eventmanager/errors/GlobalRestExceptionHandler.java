package events.paiya.eventmanager.errors;

import events.paiya.eventmanager.resources.ApiErrorResource;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.zip.DataFormatException;

@Log4j2
@RestControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler({DataFormatException.class, DateTimeParseException.class, IllegalArgumentException.class, HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResource> handleMalformedRequest(Exception ex){
        ApiErrorResource apiErrorResource = new ApiErrorResource("paiya.eventmanager.internal.error.message",
                ex.getMessage(), HttpStatus.BAD_REQUEST, null);
        log.error(ex.getMessage());
        return ResponseEntity.ofNullable(apiErrorResource);
    }

    @ExceptionHandler({NoSuchElementException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiErrorResource> handleNotFound(Exception ex){
        ApiErrorResource apiErrorResource = new ApiErrorResource("paiya.eventmanager.internal.error.message",
                ex.getMessage(), HttpStatus.NOT_FOUND, null);
        return ResponseEntity.ofNullable(apiErrorResource);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<ApiErrorResource> handleNotAllowedRequest(Exception ex){
        ApiErrorResource apiErrorResource = new ApiErrorResource("paiya.eventmanager.internal.error.message",
                ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED, null);
        return ResponseEntity.ofNullable(apiErrorResource);
    }
}
