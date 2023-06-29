package s3.algorithalliance.servicelayer.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TestSetNotFoundException extends ResponseStatusException {
    public TestSetNotFoundException() {
        super(HttpStatus.NOT_FOUND, "TEST SET COULD NOT BE FOUND");
    }

    public TestSetNotFoundException(String errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }
}