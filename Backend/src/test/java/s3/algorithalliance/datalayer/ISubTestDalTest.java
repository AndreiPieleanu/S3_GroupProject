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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ISubTestDalTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ISubTestDal subTestDal;
    private TestEntity testEntity;
    @BeforeEach
    public void setUp() {
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
        
        TestSetEntity testSetEntity = TestSetEntity
                .builder()
                .name("test set 1")
                .testResults(new HashMap<>())
                .commitEntity(commit)
                .build();
        entityManager.persist(testSetEntity);

        testEntity = TestEntity
                .builder()
                .name("test 1")
                .subtests(new HashMap<>())
                .testSet(testSetEntity)
                .duration("00:02:33")
                .build();
        entityManager.persist(testEntity);

        SubtestEntity subtestEntity = SubtestEntity
                .builder()
                .name("subtest")
                .testResult(testEntity)
                .testSteps(new HashMap<>())
                .build();
        entityManager.persist(subtestEntity);
    }
    @Test
    public void testSaveSubtest() {
        SubtestEntity subtestEntity = SubtestEntity.builder()
                .name("Subtest 2")
                .testSteps(new HashMap<>())
                .testResult(testEntity)
                .build();

        SubtestEntity savedSubtest = subTestDal.save(subtestEntity);

        assertNotNull(savedSubtest.getId());
        assertEquals(subtestEntity.getName(), savedSubtest.getName());
        assertEquals(subtestEntity.getTestResult(), savedSubtest.getTestResult());
        assertEquals(subtestEntity.getTestSteps(), savedSubtest.getTestSteps());
    }

    @Test
    public void testFindById() {
        SubtestEntity subtestEntity = subTestDal.findById(1).orElse(null);

        assertNotNull(subtestEntity);
        assertEquals("subtest", subtestEntity.getName());
    }

    @Test
    public void testGetAllSubtestsOfTestResultWithId() {
        List<SubtestEntity> subtests = subTestDal.getAllSubtestsOfTestResultWithId(testEntity.getId());

        assertNotNull(subtests);
        assertEquals(1, subtests.size());
        assertEquals("subtest", subtests.get(0).getName());
    }

    @Test
    void getCountSubTestsOfTestWithId() {
        Integer result = subTestDal.getCountSubTestsOfTestWithId(testEntity.getId());
        assertNotNull(result);
        assertEquals(1, result);
    }
}