package s3.algorithalliance.servicelayer.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import s3.algorithalliance.datalayer.IUserDal;
import s3.algorithalliance.datalayer.entities.UserEntity;
import s3.algorithalliance.domain.AccessToken;
import s3.algorithalliance.domain.reqresp.login.LoginRequest;
import s3.algorithalliance.domain.reqresp.login.LoginResponse;
import s3.algorithalliance.servicelayer.IAccessTokenEncoder;
import s3.algorithalliance.servicelayer.ILoginService;
import s3.algorithalliance.servicelayer.exc.InvalidCredentialsException;

@Service
@RequiredArgsConstructor
public class LoginService implements ILoginService {
    private final IUserDal userDal;
    private final PasswordEncoder passwordEncoder;
    private final IAccessTokenEncoder accessTokenEncoder;
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity user =
                userDal.findByEmail(loginRequest.getEmail()).orElseThrow(InvalidCredentialsException::new);
        if(!matchesPassword(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = generateAccessToken(user);
        return LoginResponse.builder().accessToken(accessToken).build();
    }
    private boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    private String generateAccessToken(UserEntity entity){
        Integer userId = null;
        if(entity.getId() != null){
            userId = entity.getId();
        }

        String userRole = entity.getUserRole().getRole().toString();

        return accessTokenEncoder.encode(
                AccessToken
                        .builder()
                        .subject(entity.getEmail())
                        .role(userRole)
                        .userId(userId)
                        .build()
        );
    }
}