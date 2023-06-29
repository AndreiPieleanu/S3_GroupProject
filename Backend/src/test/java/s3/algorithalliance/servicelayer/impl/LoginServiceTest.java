package s3.algorithalliance.servicelayer.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import s3.algorithalliance.datalayer.IUserDal;
import s3.algorithalliance.datalayer.IUserRoleDal;
import s3.algorithalliance.datalayer.entities.RoleEnum;
import s3.algorithalliance.datalayer.entities.UserEntity;
import s3.algorithalliance.datalayer.entities.UserRoleEntity;
import s3.algorithalliance.domain.AccessToken;
import s3.algorithalliance.domain.reqresp.login.LoginRequest;
import s3.algorithalliance.domain.reqresp.login.LoginResponse;
import s3.algorithalliance.servicelayer.IAccessTokenEncoder;
import s3.algorithalliance.servicelayer.ILoginService;
import s3.algorithalliance.servicelayer.IUserService;
import s3.algorithalliance.servicelayer.exc.InvalidCredentialsException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
    IUserDal userDal = mock(IUserDal.class);
    IAccessTokenEncoder accessTokenEncoder = mock(IAccessTokenEncoder.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    ILoginService loginService = new LoginService(userDal, passwordEncoder, accessTokenEncoder);
    private final UserEntity foundUser =
            UserEntity.builder().id(1).userRole(UserRoleEntity.builder().id(1).role(RoleEnum.USER).build()).username("tbuskens").email("tubskens@gmail.com").build();
    private final String accessToken = "Bearer 123";
    @Test
    public void loginShouldReturnToken_WhenDataIsValid(){
        // Arrange 
        LoginRequest request = new LoginRequest("tbuskens@gmail.com", "tom123");
        when(userDal.findByEmail(request.getEmail())).thenReturn(Optional.of(foundUser));
        when(passwordEncoder.matches(request.getPassword(),
                foundUser.getPassword())).thenReturn(true);
        when(accessTokenEncoder.encode(any(AccessToken.class))).thenReturn(accessToken);
        // Act 
        LoginResponse response = loginService.login((request));
        // Assert 
        verify(userDal).findByEmail(request.getEmail());
        assertNotNull(response);
        assertEquals(accessToken, response.getAccessToken());
    }
    @Test
    public void loginShouldThrowException_WhenUserIsNotFound(){
        // Arrange 
        LoginRequest badRequest = new LoginRequest("tbuskens@gmail.com", 
                "tom123");
        when(userDal.findByEmail(badRequest.getEmail())).thenReturn(Optional.empty());
        // Act 
        Throwable thrown = assertThrows(InvalidCredentialsException.class,
                () -> loginService.login(badRequest));
        // Assert
        assertNotNull(thrown);
        assertEquals("400 BAD_REQUEST \"INVALID_CREDENTIALS\"", thrown.getMessage());
    }
    @Test
    public void loginShouldThrowException_WhenPasswordIsIncorrect(){
        // Arrange 
        LoginRequest badRequest = new LoginRequest("tbuskens@gmail.com", 
                "tommy123");
        when(userDal.findByEmail(badRequest.getEmail())).thenReturn(Optional.of(foundUser));
        when(passwordEncoder.matches(badRequest.getPassword(),
                foundUser.getPassword())).thenReturn(false);
        when(accessTokenEncoder.encode(any(AccessToken.class))).thenReturn(accessToken);
        // Act 
        Throwable thrown = assertThrows(InvalidCredentialsException.class,
                () -> loginService.login(badRequest));
        // Assert
        assertNotNull(thrown);
        assertEquals("400 BAD_REQUEST \"INVALID_CREDENTIALS\"", thrown.getMessage());
    }
}