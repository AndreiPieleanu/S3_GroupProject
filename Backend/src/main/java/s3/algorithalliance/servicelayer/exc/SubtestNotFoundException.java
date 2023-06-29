package s3.algorithalliance.servicelayer.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SubtestNotFoundException  extends ResponseStatusException {
    public SubtestNotFoundException() {
        super(HttpStatus.NOT_FOUND, "SUBTEST COULD NOT BE FOUND");
    }

    public SubtestNotFoundException(String errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }
}