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
import s3.algorithalliance.datalayer.entities.TestEntity;
import s3.algorithalliance.datalayer.entities.TestSetEntity;

import javax.persistence.EntityManager;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ITestDalTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ITestDal testDal;
    private TestSetEntity testSet;
    @BeforeEach
    public void setUp(){
        BranchEntity branch = BranchEntity.builder()
                .name("main")
                .commits(new HashMap<>())
                .build();
        entityManager.persist(branch);

        CommitEntity commit = CommitEntity.builder()
                .name("initial commit")
                .originBranch(branch)
                .testSets(new HashMap<>())
                .version("12.34.56.78")
                .build();
        entityManager.persist(commit);
        
        testSet = TestSetEntity.builder()
                .name("TestSet1")
                .testResults(new HashMap<>())
                .commitEntity(commit)
                .build();
        entityManager.persist(testSet);
    }
    @Test
    public void testSaveAndGetById() {
        TestEntity test = TestEntity.builder()
                .name("Test1")
                .testSet(testSet)
                .duration("00:02:33")
                .subtests(new HashMap<>())
                .build();
        entityManager.persist(test);

        TestEntity savedTest = testDal.findById(test.getId()).orElse(null);
        assertNotNull(savedTest);
        assertEquals(test.getName(), savedTest.getName());
        assertEquals(test.getTestSet().getId(), savedTest.getTestSet().getId());
    }

    @Test
    public void testGetAllTestResultsOfTestSetWithId() {
        TestEntity test1 = TestEntity.builder()
                .name("Test1")
                .testSet(testSet)
                .subtests(new HashMap<>())
                .duration("00:02:33")
                .build();
        entityManager.persist(test1);

        TestEntity test2 = TestEntity.builder()
                .name("Test2")
                .testSet(testSet)
                .subtests(new HashMap<>())
                .duration("00:02:33")
                .build();
        entityManager.persist(test2);

        List<TestEntity> testResults = testDal.getAllTestResultsOfTestSetWithId(testSet.getId());
        assertNotNull(testResults);
        assertEquals(2, testResults.size());
        assertEquals(testResults.get(0), test1);
        assertEquals(testResults.get(1), test2);
    }

    @Test
    void getCountTestsOfTestSetWithId() {
        Integer result = testDal.getCountTestsOfTestSetWithId(testSet.getId());
        assertNotNull(result);
        assertEquals(0, result);
    }
}