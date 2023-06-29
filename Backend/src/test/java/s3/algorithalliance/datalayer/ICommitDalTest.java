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


import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ICommitDalTest {
    @Autowired
    private ICommitDal commitDal;

    @Autowired
    private IBranchDal branchDal;

    private BranchEntity branch;

    private CommitEntity commit;

    @BeforeEach
    public void setUp(){
        branch = BranchEntity.builder()
                .name("main")
                .commits(new HashMap<>())
                .build();
        branch = branchDal.save(branch);

        commit = CommitEntity.builder()
                .name("initial commit")
                .originBranch(branch)
                .testSets(new HashMap<>())
                .version("12.34.56.78")
                .build();
        commit = commitDal.save(commit);
    }

    @Test
    public void testGetAllEntitiesOfBranch() {
        // Act 
        List<CommitEntity> result = commitDal.getAllEntitiesOfBranch(branch.getId());
        // Assert 
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(commit.getName(), result.get(0).getName());
        assertEquals(commit.getOriginBranch().getId(), result.get(0).getOriginBranch().getId());
        assertEquals(commit.getTestSets().size(), result.get(0).getTestSets().size());
    }

    @Test
    public void testSaveCommit() {
        // Arrange 
        CommitEntity newCommit = CommitEntity.builder()
                .name("second commit")
                .originBranch(branch)
                .testSets(new HashMap<>())
                .version("12.34.56.78")
                .build();
        // Act 
        CommitEntity savedCommit = commitDal.save(newCommit);
        // Assert 
        assertNotNull(savedCommit.getId());
        assertEquals(newCommit.getName(), savedCommit.getName());
        assertEquals(newCommit.getOriginBranch().getId(), savedCommit.getOriginBranch().getId());
        assertEquals(newCommit.getTestSets().size(), savedCommit.getTestSets().size());
    }

    @Test
    void getCountCommitsOfBranchWithId() {
        Integer result = commitDal.getCountCommitsOfBranchWithId(commit.getId());
        assertNotNull(result);
        assertEquals(1, result);
    }
}