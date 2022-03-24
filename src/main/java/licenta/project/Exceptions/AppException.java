package licenta.project.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class AppException extends HttpClientErrorException {
    public AppException(HttpStatus status, String message) {
        super(status, message);
    }
}
