package wrapsto.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class Conflict extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public Conflict(String message) {
        super(message);
    }
}
