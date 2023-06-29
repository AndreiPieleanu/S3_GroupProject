package s3.algorithalliance.servicelayer.exc;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserRoleNotFoundException extends ResponseStatusException {
    public UserRoleNotFoundException() {
        super(HttpStatus.NOT_FOUND, "USER ROLE COULD NOT BE FOUND");
    }

    public UserRoleNotFoundException(String errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }
}