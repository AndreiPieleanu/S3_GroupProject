package s3.algorithalliance.servicelayer;

import s3.algorithalliance.domain.reqresp.user.*;

public interface IUserService {
    GetUserResponse getUserById(Integer id);
    GetUserResponse getUserByEmail(GetUserRequest request);
    CreateUserResponse createUser(CreateUserRequest request);
    UpdateUserResponse updateUser(UpdateUserRequest request);
}
