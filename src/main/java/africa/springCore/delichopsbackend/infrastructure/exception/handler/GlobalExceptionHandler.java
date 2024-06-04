package africa.springCore.delichopsbackend.infrastructure.exception.handler;

import africa.springCore.delichopsbackend.infrastructure.exception.DeliChopsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private final ExceptionResponse exceptionResponse = new ExceptionResponse();

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> deliChopsException(DeliChopsException ex){
        exceptionResponse.setMessage(ex.getLocalizedMessage());
        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setErrorCode(BAD_REQUEST.value());
        log.error("DeliChopsException::>> {}", exceptionResponse);
        return new ResponseEntity<>(exceptionResponse, BAD_REQUEST);
    }
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> deliChopsException(Exception ex){
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setErrorCode(BAD_REQUEST.value());
        log.error("Exception::>> {}", exceptionResponse);
        return new ResponseEntity<>(exceptionResponse, BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        exceptionResponse.setMessage(errors.toString());
        exceptionResponse.setTimestamp(LocalDateTime.now());
        exceptionResponse.setErrorCode(BAD_REQUEST.value());
        log.error("MethodArgumentNotValidException::>> {}", exceptionResponse);
        return new ResponseEntity<>(exceptionResponse, BAD_REQUEST);
    }
}
