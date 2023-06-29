package s3.algorithalliance.servicelayer.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BranchNotFoundException extends ResponseStatusException {
    public BranchNotFoundException() {
        super(HttpStatus.NOT_FOUND, "BRANCH COULD NOT BE FOUND");
    }

    public BranchNotFoundException(String errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }
}