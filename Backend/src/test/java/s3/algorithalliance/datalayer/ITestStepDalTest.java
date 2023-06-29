package s3.algorithalliance.datalayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import s3.algorithalliance.datalayer.entities.*;

import javax.persistence.EntityManager;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ITestStepDalTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ITestStepDal testStepDal;
    private SubtestEntity subtestEntity;
    private TestErrorEntity testErrorEntity;
    private TestWarningEntity testWarningEntity;
    private ResultTypeEntity resultTypeEntity;
    private TestEntity testEntity;
    @BeforeEach
    public void setUp(){
        BranchEntity branch = BranchEntity.builder()
                .name("main")
                .commits(new HashMap<>())
                .build();
        entityManager.persist(branch);

        CommitEntity commitEntity = CommitEntity.builder()
                .name("initial commit")
                .originBranch(branch)
                .testSets(new HashMap<>())
                .version("12.34.56.78")
                .build();
        entityManager.persist(commitEntity);

        TestSetEntity testSetEntity = TestSetEntity.builder()
                .name("TestSet1")
                .testResults(new HashMap<>())
                .commitEntity(commitEntity)
                .build();
        entityManager.persist(testSetEntity);
        
        testEntity = TestEntity.builder()
                .name("Test1")
                .testSet(testSetEntity)
                .subtests(new HashMap<>())
                .duration("00:02:33")
                .build();
        entityManager.persist(testEntity);

        subtestEntity = SubtestEntity.builder()
                .name("Subtest1")
                .testResult(testEntity)
                .testSteps(new HashMap<>())
                .build();
        entityManager.persist(subtestEntity);

        testErrorEntity = TestErrorEntity.builder()
                .errorCode("CMX")
                .build();
        entityManager.persist(testErrorEntity);
        
        testWarningEntity = TestWarningEntity.builder()
                .warningCode(1)
                .build();
        entityManager.persist(testWarningEntity);
        
        resultTypeEntity = ResultTypeEntity.builder()
                .resultName("Result1")
                .build();
        entityManager.persist(resultTypeEntity);
    }
    @Test
    public void testSaveAndFindById() {
        // Create a new test step entity
        TestStepEntity testStep = TestStepEntity.builder()
                .stepNo(1)
                .result_details("Success")
                .subtest(subtestEntity)
                .testWarning(testWarningEntity)
                .testError(testErrorEntity)
                .resultType(resultTypeEntity)
                .build();

        // Persist the entity using the entity manager
        entityManager.persist(testStep);

        // Find the entity by id using the repository
        TestStepEntity foundTestStep = testStepDal.findById(testStep.getId()).orElse(null);

        // Check if the found entity is the same as the original entity
        assertNotNull(foundTestStep);
        assertEquals(testStep.getId(), foundTestStep.getId());
        assertEquals(testStep.getStepNo(), foundTestStep.getStepNo());
        assertEquals(testStep.getResult_details(), foundTestStep.getResult_details());
    }

    @Test
    public void testGetAllTestStepsOfSubtestWithId() {
        // Create a new subtest entity
        SubtestEntity subtest = SubtestEntity.builder()
                .name("Subtest 2")
                .testSteps(new HashMap<>())
                .testResult(testEntity)
                .build();

        // Persist the subtest entity using the entity manager
        entityManager.persist(subtest);

        // Create two new test step entities and link them to the subtest entity
        TestStepEntity testStep1 = TestStepEntity.builder()
                .stepNo(1)
                .result_details("Success")
                .subtest(subtest)
                .testWarning(testWarningEntity)
                .testError(testErrorEntity)
                .resultType(resultTypeEntity)
                .build();

        TestStepEntity testStep2 = TestStepEntity.builder()
                .stepNo(2)
                .result_details("Failure")
                .subtest(subtest)
                .testWarning(testWarningEntity)
                .testError(testErrorEntity)
                .resultType(resultTypeEntity)
                .build();

        // Persist the test step entities using the entity manager
        entityManager.persist(testStep1);
        entityManager.persist(testStep2);

        // Retrieve the list of test step entities linked to the subtest entity
        List<TestStepEntity> testSteps = testStepDal.getAllTestStepsOfSubtestWithId(subtest.getId());

        // Check if the list contains the correct number of test steps
        assertEquals(2, testSteps.size());

        // Check if the list contains the correct test step entities
        assertTrue(testSteps.contains(testStep1));
        assertTrue(testSteps.contains(testStep2));
    }

    @Test
    void getCountTestStepsOfSubtestWithId() {
        Integer result = testStepDal.getCountTestStepsOfSubtestWithId(subtestEntity.getId());
        assertNotNull(result);
        assertEquals(0, result);
    }
}