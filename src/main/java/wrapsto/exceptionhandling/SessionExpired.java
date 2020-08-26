package wrapsto.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
public class SessionExpired extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SessionExpired(String message) {
        super(message);
    }
}

