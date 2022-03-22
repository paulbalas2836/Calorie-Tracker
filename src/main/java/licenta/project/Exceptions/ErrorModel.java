package licenta.project.Exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ErrorModel {
    private final HttpStatus status;
    private final String message;
    private final List<String> errors;

    public ErrorModel(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ErrorModel(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = List.of(error);
    }
}
