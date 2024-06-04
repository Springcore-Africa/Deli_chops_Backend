package africa.springCore.delichopsbackend.infrastructure.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ToString
public class ExceptionResponse {
    private LocalDateTime timestamp;
    private String message;
    private int errorCode;
    private List<String> details;
    private String globalisationMessageCode;

    public ExceptionResponse(LocalDateTime timestamp, String message, List<String> details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public ExceptionResponse(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = Collections.singletonList(details);
    }

    public ExceptionResponse(LocalDateTime timestamp, String message, String details, String globalisationMessageCode) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = Collections.singletonList(details);
        this.globalisationMessageCode = globalisationMessageCode;
    }

    public static ExceptionResponse getCodeAndNarration(RuntimeException e, WebRequest request) {
        return new ExceptionResponse(
                LocalDateTime.now(), e.getLocalizedMessage(), request.getDescription(false));
    }


}
