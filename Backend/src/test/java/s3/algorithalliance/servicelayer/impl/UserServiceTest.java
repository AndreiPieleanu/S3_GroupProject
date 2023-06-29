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
import s3.algorithalliance.domain.User;
import s3.algorithalliance.domain.reqresp.user.*;
import s3.algorithalliance.servicelayer.IUserService;
import s3.algorithalliance.servicelayer.exc.UserNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    IUserDal userDal = mock(IUserDal.class);
    IUserRoleDal userRoleDal = mock(IUserRoleDal.class);
    PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    IUserService userService = new UserService(userDal, userRoleDal, passwordEncoder);
    private final UserEntity existentUserEntity =
            UserEntity.builder().id(1).username("JohnDoe").build();
    private final User existentUser =
            User.builder().id(1).username("JohnDoe").build();
    private final UserRoleEntity roleEntity =
            UserRoleEntity.builder().id(1).role(RoleEnum.USER).build();
    @Test
    public void getUserByIdShouldReturnUser_WhenUserExists(){
        // Arrange
        Integer idToFind = 1;
        when(userDal.findById(existentUser.getId())).thenReturn(Optional.of(existentUserEntity));
        // Act 
        GetUserResponse response = userService.getUserById(idToFind);
        // Assert 
        verify(userDal).findById(idToFind);
        assertNotNull(response);
        assertEquals(existentUser, response.getFoundUser());
    }
    @Test
    public void getUserByIdShouldThrowException_WhenUserDoesNotExist(){
        // Arrange 
        Integer nonExistentId = 10;
        when(userDal.findById(nonExistentId)).thenReturn(Optional.empty());
        // Act 
        Throwable thrown = assertThrows(UserNotFoundException.class,
                () -> userService.getUserById(nonExistentId));
        // Assert
        assertNotNull(thrown);
        assertEquals(thrown.getMessage(), "404 NOT_FOUND \"USER COULD NOT BE FOUND\"");
    }
    @Test
    public void createUserShouldCreate_WhenUserIsNew(){
        // Arrange 
        CreateUserRequest request = CreateUserRequest
                .builder()
                .email("andrei@gmail.com")
                .username("andrei")
                .password("andrei123")
                .build();
        UserEntity createdUser = UserEntity.builder().id(2).email("andrei" +
                        "@gmail.com").username("andrei").password("andrei123").build();
        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());
        when(userRoleDal.findById(RoleEnum.USER.getValue())).thenReturn(Optional.of(roleEntity));
        when(userDal.save(any(UserEntity.class))).thenReturn(createdUser);
        // Act 
        CreateUserResponse response = userService.createUser(request);
        // Assert 
        verify(userDal).save(any(UserEntity.class));
        assertNotNull(response);
    }
    @Test
    public void updateUserUsernameShouldUpdate_WhenUserExists(){
        // Arrange 
        UpdateUserRequest request = UpdateUserRequest
                .builder()
                .id(1)
                .email("tbuskens@gmail.com")
                .username("andrei")
                .password("tom123")
                .build();
        UserEntity userToUpdate = UserEntity.builder().id(1).email("tbuskens" +
                "@gmail.com").username("andrei").password("tom123").userRole(roleEntity).build();
        Integer updatedRows = 1;
        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());
        when(userRoleDal.findById(RoleEnum.USER.getValue())).thenReturn(Optional.of(roleEntity));
        when(userDal.updateUser(userToUpdate)).thenReturn(updatedRows);
        // Act 
        UpdateUserResponse response = userService.updateUser(request);
        // Assert 
        verify(userDal).updateUser(userToUpdate);
        assertNotNull(response);
        assertEquals(updatedRows, response.getUpdatedRows());
    }
    @Test
    public void testGetUserByEmail(){
        UserEntity userToFind = UserEntity.builder().id(1).email("tbuskens" +
                "@gmail.com").username("andrei").password("tom123").userRole(roleEntity).build();
        User foundUser = User.builder().id(1).email("tbuskens" +
                "@gmail.com").username("andrei").build();
        GetUserRequest request = new GetUserRequest(userToFind.getEmail());
        when(userDal.findByEmail(userToFind.getEmail())).thenReturn(Optional.of(userToFind));
        GetUserResponse response = userService.getUserByEmail(request);
        assertNotNull(response);
        assertEquals(foundUser, response.getFoundUser());
    }
    @Test
    public void testGetUserByEmailThrowsException(){
        UserEntity userToFind = UserEntity.builder().id(1).email("tbuskens" +
                "@gmail.com").username("andrei").password("tom123").userRole(roleEntity).build();
        GetUserRequest request = new GetUserRequest(userToFind.getEmail());
        when(userDal.findByEmail(userToFind.getEmail())).thenReturn(Optional.empty());
        Throwable thrown = assertThrows(UserNotFoundException.class,
                () -> userService.getUserByEmail(request)); 
        assertNotNull(thrown);
        assertEquals("404 NOT_FOUND \"USER COULD NOT BE FOUND\"", thrown.getMessage());
    }
}