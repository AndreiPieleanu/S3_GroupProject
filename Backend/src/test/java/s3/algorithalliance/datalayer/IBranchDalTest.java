package s3.algorithalliance.datalayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import s3.algorithalliance.datalayer.entities.BranchEntity;
import s3.algorithalliance.datalayer.entities.RoleEnum;
import s3.algorithalliance.datalayer.entities.UserEntity;
import s3.algorithalliance.datalayer.entities.UserRoleEntity;

import javax.persistence.EntityManager;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IBranchDalTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private IBranchDal branchDal;
    @Autowired
    private IUserRoleDal userRoleDal;
    private UserEntity user;
    @BeforeEach
    public void setUp(){
        UserRoleEntity role =
                userRoleDal.findByRole(RoleEnum.USER).orElse(UserRoleEntity
                .builder()
                .role(RoleEnum.USER)
                .build());
        user = UserEntity
                .builder()
                .userRole(role)
                .branches(new HashMap<>())
                .username("tom")
                .email("tbuskens@gmail.com")
                .password("tom123")
                .build();
        entityManager.persist(user);
    }
    @Test
    public void testSaveBranch() {
        // Arrange 
        BranchEntity branch = BranchEntity.builder()
                .name("main")
                .userEntity(user)
                .commits(new HashMap<>())
                .build();
        // Act 
        BranchEntity savedBranch = branchDal.save(branch);
        // Assert 
        assertNotNull(savedBranch.getId());
        assertEquals(branch.getName(), savedBranch.getName());
        assertEquals(branch.getUserEntity(), savedBranch.getUserEntity());
        assertEquals(branch.getCommits(), savedBranch.getCommits());
    }

    @Test
    public void testFindAllPublicBranches() {
        // Arrange 
        BranchEntity publicBranch = BranchEntity.builder()
                .name("public")
                .userEntity(null)
                .commits(new HashMap<>())
                .build();
        BranchEntity privateBranch = BranchEntity.builder()
                .name("private")
                .userEntity(user)
                .commits(new HashMap<>())
                .build();
        // Act 
        branchDal.save(publicBranch);
        branchDal.save(privateBranch);
        // Assert 
        List<BranchEntity> publicBranches = branchDal.findAllPublicBranches();
        assertEquals(1, publicBranches.size());
        assertEquals(publicBranch, publicBranches.get(0));
    }
}