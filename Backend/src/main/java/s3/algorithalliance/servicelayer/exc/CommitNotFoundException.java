package s3.algorithalliance.servicelayer.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CommitNotFoundException extends ResponseStatusException {
    public CommitNotFoundException() {
        super(HttpStatus.NOT_FOUND, "COMMIT COULD NOT BE FOUND");
    }

    public CommitNotFoundException(String errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }
}