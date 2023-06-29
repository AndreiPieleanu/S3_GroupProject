package s3.algorithalliance.servicelayer.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TestStepNotFoundException  extends ResponseStatusException {
    public TestStepNotFoundException() {
        super(HttpStatus.NOT_FOUND, "TEST STEP COULD NOT BE FOUND");
    }

    public TestStepNotFoundException(String errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }
}