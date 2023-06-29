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
import s3.algorithalliance.datalayer.entities.CommitEntity;
import s3.algorithalliance.datalayer.entities.TestSetEntity;

import javax.persistence.EntityManager;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ITestSetDalTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ITestSetDal testSetDal;
    private TestSetEntity testSetEntity;
    private CommitEntity commitEntity;

    @BeforeEach
    public void setUp(){
        BranchEntity branch = BranchEntity.builder()
                .name("main")
                .commits(new HashMap<>())
                .build();
        entityManager.persist(branch);

         commitEntity = CommitEntity.builder()
                .name("initial commit")
                .originBranch(branch)
                .testSets(new HashMap<>())
                .version("12.34.56.78")
                .build();
        entityManager.persist(commitEntity);

        testSetEntity = TestSetEntity.builder()
                .name("TestSet1")
                .testResults(new HashMap<>())
                .commitEntity(commitEntity)
                .build();
        entityManager.persist(testSetEntity);
    }
    @Test
    public void testSaveAndGetById() {
        // get the TestSetEntity instance by id using the dal
        TestSetEntity retrievedTestSetEntity = testSetDal.findById(testSetEntity.getId()).orElse(null);

        // assert that the retrieved TestSetEntity instance matches the saved instance
        assertNotNull(retrievedTestSetEntity);
        assertEquals(testSetEntity, retrievedTestSetEntity);
    }

    @Test
    public void testGetAllTestSetsOfCommitWithId() {
        BranchEntity otherBranch = BranchEntity.builder()
                .name("")
                .commits(new HashMap<>())
                .build();
        entityManager.persist(otherBranch);

        CommitEntity otherCommit = CommitEntity.builder()
                .name("initial commit")
                .originBranch(otherBranch)
                .testSets(new HashMap<>())
                .version("12.34.56.78")
                .build();
        entityManager.persist(otherCommit);
        
        TestSetEntity testSetEntity1 = TestSetEntity.builder()
                .name("Test Set 1")
                .commitEntity(otherCommit)
                .testResults(new HashMap<>())
                .build();
        TestSetEntity testSetEntity2 = TestSetEntity.builder()
                .name("Test Set 2")
                .commitEntity(otherCommit)
                .testResults(new HashMap<>())
                .build();
        // save them using the EntityManager
        entityManager.persist(testSetEntity1);
        entityManager.persist(testSetEntity2);

        // get the list of TestSetEntity instances by commitEntity id using the dal
        List<TestSetEntity> retrievedTestSetEntities = testSetDal.getAllTestSetsOfCommitWithId(otherCommit.getId());

        // assert that the retrieved list contains both TestSetEntity instances
        assertEquals(2, retrievedTestSetEntities.size());
        assertTrue(retrievedTestSetEntities.contains(testSetEntity1));
        assertTrue(retrievedTestSetEntities.contains(testSetEntity2));
    }

    @Test
    void getCountTestSetsOfCommitWithId() {
        Integer result = testSetDal.getCountTestSetsOfCommitWithId(commitEntity.getId());
        assertNotNull(result);
        assertEquals(1, result);
    }
}