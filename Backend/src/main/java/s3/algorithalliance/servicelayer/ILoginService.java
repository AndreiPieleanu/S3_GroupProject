package s3.algorithalliance.servicelayer;

import s3.algorithalliance.domain.reqresp.login.*;

public interface ILoginService {
    LoginResponse login(LoginRequest loginRequest);
}
