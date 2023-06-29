package s3.algorithalliance.servicelayer.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TestResultNotFoundException extends ResponseStatusException {
    public TestResultNotFoundException() {
        super(HttpStatus.NOT_FOUND, "TEST COULD NOT BE FOUND");
    }

    public TestResultNotFoundException(String errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }
}