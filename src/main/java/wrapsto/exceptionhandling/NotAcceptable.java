package wrapsto.exceptionhandling;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptable extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotAcceptable(String message) {
        super(message);
    }
}