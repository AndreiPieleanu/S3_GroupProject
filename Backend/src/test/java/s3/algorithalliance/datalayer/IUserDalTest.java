package s3.algorithalliance.datalayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import s3.algorithalliance.datalayer.entities.RoleEnum;
import s3.algorithalliance.datalayer.entities.UserEntity;
import s3.algorithalliance.datalayer.entities.UserRoleEntity;

import javax.persistence.EntityManager;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IUserDalTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private IUserDal userDal;
    private UserRoleEntity role;
    @BeforeEach
    public void setUp(){
        role = UserRoleEntity.builder().role(RoleEnum.USER).build();
        entityManager.persist(role);
    }
    @Test
    public void testSaveUser() {
        UserEntity user = UserEntity.builder()
                .email("test@example.com")
                .username("user")
                .password("testpassword")
                .userRole(role)
                .branches(new HashMap<>())
                .build();

        user = userDal.save(user);

        assertNotNull(user);
        assertNotNull(user.getId());
    }
    @Test
    public void testFindUserById() {
        UserEntity user = UserEntity.builder()
                .email("test@example.com")
                .username("user")
                .password("testpassword")
                .userRole(role)
                .branches(new HashMap<>())
                .build();
        entityManager.persist(user);
        
        UserEntity foundUser = userDal.findById(user.getId()).orElse(null);

        assertNotNull(foundUser);
        assertEquals(user, foundUser);
    }

    @Test
    public void testUpdateUser() {
        UserEntity user = UserEntity.builder()
                .email("test@example.com")
                .username("user")
                .password("testpassword")
                .userRole(role)
                .branches(new HashMap<>())
                .build();

        entityManager.persist(user);

        user.setEmail("newtest@example.com");
        user.setUsername("new");
        user.setPassword("newtestpassword");

        Integer updatedCount = userDal.updateUser(user);

        assertEquals(1, updatedCount);

        UserEntity updatedUser = entityManager.find(UserEntity.class, user.getId());
        
        assertEquals(user.getEmail(), updatedUser.getEmail());
        assertEquals(user.getUsername(), updatedUser.getUsername());
        assertEquals(user.getPassword(), updatedUser.getPassword());
    }

    @Test
    public void testFindByEmail() {
        UserEntity user = UserEntity.builder()
                .email("test@example.com")
                .username("user")
                .password("testpassword")
                .userRole(role)
                .branches(new HashMap<>())
                .build();

        entityManager.persist(user);

        Optional<UserEntity> foundUser = userDal.findByEmail(user.getEmail());

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }
}