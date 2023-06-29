package s3.algorithalliance.servicelayer.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import s3.algorithalliance.datalayer.IUserDal;
import s3.algorithalliance.datalayer.IUserRoleDal;
import s3.algorithalliance.datalayer.entities.RoleEnum;
import s3.algorithalliance.datalayer.entities.UserEntity;
import s3.algorithalliance.datalayer.entities.UserRoleEntity;
import s3.algorithalliance.domain.User;
import s3.algorithalliance.domain.converters.UserConverter;
import s3.algorithalliance.domain.reqresp.user.*;
import s3.algorithalliance.servicelayer.IUserService;
import s3.algorithalliance.servicelayer.exc.UserNotFoundException;
import s3.algorithalliance.servicelayer.exc.UserRoleNotFoundException;

import javax.transaction.Transactional;
import java.util.HashMap;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private IUserDal userDal;
    private IUserRoleDal userRoleDal;
    private final PasswordEncoder passwordEncoder;

    @Override
    public GetUserResponse getUserById(Integer id) {
        UserEntity foundUser =
                userDal.findById(id).orElseThrow(UserNotFoundException::new);
        return new GetUserResponse(UserConverter.convert(foundUser));
    }

    @Override
    public GetUserResponse getUserByEmail(GetUserRequest request) {
        UserEntity foundUser =
                userDal.findByEmail(request.getEmail()).orElseThrow(UserNotFoundException::new);
        return new GetUserResponse(UserConverter.convert(foundUser));
    }
    @Transactional
    @Override
    public CreateUserResponse createUser(CreateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserRoleEntity userRole =
                userRoleDal.findById(RoleEnum.USER.getValue())
                .orElseThrow(UserRoleNotFoundException::new);
        
        UserEntity userToAdd = UserEntity
                .builder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(encodedPassword)
                .userRole(userRole)
                .branches(new HashMap<>())
                .build();
        UserEntity createdUser = userDal.save(userToAdd);
        return new CreateUserResponse(createdUser.getId());
    }

    @Override
    public UpdateUserResponse updateUser(UpdateUserRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserRoleEntity userRole =
                userRoleDal.findById(RoleEnum.USER.getValue())
                        .orElseThrow(UserRoleNotFoundException::new);
        
        UserEntity updatedUser = UserEntity
                .builder()
                .userRole(userRole)
                .id(request.getId())
                .username(request.getUsername())
                .password(encodedPassword)
                .email(request.getEmail())
                .build();
        Integer response = userDal.updateUser(updatedUser);
        return new UpdateUserResponse(response);
    }
}
