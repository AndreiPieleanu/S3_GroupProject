package s3.algorithalliance.servicelayer.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import s3.algorithalliance.datalayer.IBranchDal;
import s3.algorithalliance.datalayer.entities.BranchEntity;
import s3.algorithalliance.datalayer.entities.UserEntity;
import s3.algorithalliance.domain.*;
import s3.algorithalliance.domain.reqresp.branches.*;
import s3.algorithalliance.domain.reqresp.commit.GetResultsForAllCommitsOfBranchWithIdResponse;
import s3.algorithalliance.domain.reqresp.commit.GetResultsForTestSetsOfAllCommitsRequest;
import s3.algorithalliance.servicelayer.IBranchService;
import s3.algorithalliance.servicelayer.ICommitService;
import s3.algorithalliance.servicelayer.exc.BranchNotFoundException;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BranchServiceTest {
    IBranchDal branchDal = mock(IBranchDal.class);
    ICommitService commitService = mock(ICommitService.class);
    IBranchService branchService = new BranchService(branchDal, commitService);
    private final List<BranchEntity> publicBranchesEntity = List.of(
            BranchEntity.builder().id(1).name("main").userEntity(null).build()
    );
    private final List<Branch> publicBranches = List.of(
            Branch.builder().id(1).name("main").user(null).build()
    );
    private final UserEntity existentUserEntity =
            UserEntity.builder().id(1).build();
    private final User existentUser =
            User.builder().id(1).build();
    private final List<BranchEntity> allBranchesEntity = List.of(
            BranchEntity.builder().id(1).name("main").userEntity(null).build(),
            BranchEntity.builder().id(2).name("tests").userEntity(existentUserEntity).build(),
            BranchEntity.builder().id(2).name("tests").userEntity(UserEntity.builder().id(3).build()).build()
    );
    private final BranchEntity existentBranchEntity =
            BranchEntity.builder().id(1).name("main").userEntity(null).build();
    @Test
    public void getPublicBranchesShouldWork_WhenPublicBranchesExist(){
        // Arrange 
        when(branchDal.findAllPublicBranches()).thenReturn(publicBranchesEntity);
        // Act 
        GetPublicBranchesResponse response = branchService.getAllPublicBranches();
        // Assert 
        verify(branchDal).findAllPublicBranches();
        assertNotNull(response);
        assertEquals(publicBranches, response.getPublicBranches().values().stream().toList());
    }
    @Test
    public void getAllBranchesOfUser_WhenUserExists(){
        // Arrange 
        GetBranchRequest request = new GetBranchRequest(existentUser.getId());
        when(branchDal.findAllBranchesForUserWithId(1)).thenReturn(allBranchesEntity);
        List<Branch> expectedList = List.of(
                Branch.builder().id(1).name("main").user(null).build(),
                Branch.builder().id(2).name("tests").user(existentUser).build()
        );
        // Act 
        GetUserBranchesResponse response =
                branchService.getBranchesOfUser(request);
        // Assert 
        verify(branchDal).findAllBranchesForUserWithId(1);
        assertNotNull(response);
        assertEquals(expectedList, response.getUserBranches().values().stream().toList());
    }
    @Test
    public void getBranchShouldReturnBranch_WhenBranchExists(){
        // Arrange 
        GetBranchRequest request = new GetBranchRequest(existentUser.getId());
        when(branchDal.findById(request.getUserId())).thenReturn(Optional.of(existentBranchEntity));
        Branch exectedBranch =
                Branch.builder().id(1).name("main").user(null).build();
        // Act 
        GetBranchResponse response = branchService.getBranch(request);
        // Assert 
        verify(branchDal).findById(request.getUserId());
        assertNotNull(response);
        assertEquals(exectedBranch, response.getBranch());
    }
    @Test
    public void getBranchShouldThrowExceptionWhenBranchDoesNotExist(){
        // Arrange
        GetBranchRequest badRequest =
                new GetBranchRequest(22);
        when(branchDal.findById(badRequest.getUserId())).thenReturn(Optional.empty());
        // Act
        Throwable thrown = assertThrows(BranchNotFoundException.class,
                () -> branchService.getBranch(badRequest));
        // Assert
        assertNotNull(thrown);
        assertEquals("404 NOT_FOUND \"BRANCH COULD NOT BE FOUND\"", thrown.getMessage());
    }
    @Test
    public void createBranchShouldWork_WhenBranchIsNew() {
        // Arrange
        BranchEntity branchToAdd = BranchEntity.builder()
                .name("main")
                .userEntity(null)
                .commits(new HashMap<>())
                .build();

        BranchEntity addedBranch = BranchEntity.builder()
                .id(1)
                .name("main")
                .userEntity(UserEntity.builder().id(1).build())
                .commits(new HashMap<>())
                .build();

        when(branchDal.save(any(BranchEntity.class))).thenReturn(addedBranch);

        CreateBranchRequest request = CreateBranchRequest.builder()
                .name(branchToAdd.getName())
                .userId(1)
                .build();

        // Act
        CreateBranchResponse response = branchService.createBranch(request);

        // Assert
        verify(branchDal).save(any(BranchEntity.class));
        assertNotNull(response);
        assertEquals(addedBranch.getId(), response.getCreatedId());
    }
    @Test
    public void testGetCommitErrors() {
        // Prepare test data
        BranchEntity branchEntity = BranchEntity.builder().id(1).build();

        Commit commit1 = Commit.builder()
                .id(1)
                .versionDate(Timestamp.valueOf("2022-01-01 00:00:00"))
                .version("123")
                .testSets(new HashMap<>())
                .build();
        Commit commit2 = Commit.builder()
                .id(2)
                .versionDate(Timestamp.valueOf("2022-02-01 00:00:00"))
                .version("456")
                .testSets(new HashMap<>())
                .build();
        Commit commit3 = Commit.builder()
                .id(3)
                .versionDate(Timestamp.valueOf("2022-03-01 00:00:00"))
                .version("789")
                .testSets(new HashMap<>())
                .build();
        Commit commit4 = Commit.builder()
                .id(4)
                .versionDate(Timestamp.valueOf("2022-04-01 00:00:00"))
                .version("987")
                .testSets(new HashMap<>())
                .build();

        TreeMap<Commit, Integer> branchResults = new TreeMap<>();
        branchResults.put(commit1, 1);
        branchResults.put(commit2, 0);
        branchResults.put(commit3, 1);
        branchResults.put(commit4, 0);

        when(commitService.getResultsForAllCommitsInBranchOrdered(any(GetResultsForTestSetsOfAllCommitsRequest.class)))
                .thenReturn(GetResultsForAllCommitsOfBranchWithIdResponse.builder().results(branchResults).build());
        when(branchDal.findAllPublicBranches()).thenReturn(List.of(branchEntity));

        // Execute the method under test
        List<CommitError> errors = branchService.getCommitErrors();

        // Verify the expected behavior and results
        assertEquals(1, errors.size());

        Commit error1 = errors.get(0).getErrorStartCommit();
        assertEquals(commit4, error1);

        Commit error2 = errors.get(0).getLastCommit();
        assertEquals(commit4, error2);

        // Verify the interactions with the mock objects
        verify(commitService, times(2)).getResultsForAllCommitsInBranchOrdered(any(GetResultsForTestSetsOfAllCommitsRequest.class));
    }
    @Test
    public void testGetCommitErrorsForAuthedUser() {
        // Prepare test data
        BranchEntity branchEntity = BranchEntity.builder().id(1).build();

        Commit commit1 = Commit.builder()
                .id(1)
                .versionDate(Timestamp.valueOf("2022-01-01 00:00:00"))
                .version("123")
                .testSets(new HashMap<>())
                .build();
        Commit commit2 = Commit.builder()
                .id(2)
                .versionDate(Timestamp.valueOf("2022-02-01 00:00:00"))
                .version("456")
                .testSets(new HashMap<>())
                .build();
        Commit commit3 = Commit.builder()
                .id(3)
                .versionDate(Timestamp.valueOf("2022-03-01 00:00:00"))
                .version("789")
                .testSets(new HashMap<>())
                .build();
        Commit commit4 = Commit.builder()
                .id(4)
                .versionDate(Timestamp.valueOf("2022-04-01 00:00:00"))
                .version("987")
                .testSets(new HashMap<>())
                .build();

        TreeMap<Commit, Integer> branchResults = new TreeMap<>();
        branchResults.put(commit1, 1);
        branchResults.put(commit2, 0);
        branchResults.put(commit3, 1);
        branchResults.put(commit4, 0);

        when(commitService.getResultsForAllCommitsInBranchOrdered(any(GetResultsForTestSetsOfAllCommitsRequest.class)))
                .thenReturn(GetResultsForAllCommitsOfBranchWithIdResponse.builder().results(branchResults).build());
        when(branchDal.findAllBranchesForUserWithId(1)).thenReturn(List.of(branchEntity));

        // Execute the method under test
        List<CommitError> errors =
                branchService.getCommitErrorsForAuthedUser(1);

        // Verify the expected behavior and results
        assertEquals(1, errors.size());

        Commit error1 = errors.get(0).getErrorStartCommit();
        assertEquals(commit4, error1);

        Commit error2 = errors.get(0).getLastCommit();
        assertEquals(commit4, error2);

        // Verify the interactions with the mock objects
        verify(commitService, times(2)).getResultsForAllCommitsInBranchOrdered(any(GetResultsForTestSetsOfAllCommitsRequest.class));
    }
    @Test
    public void testGetBranchResults() {
        // Prepare test data
        BranchEntity branchEntity1 = BranchEntity.builder().id(1).build();
        BranchEntity branchEntity2 = BranchEntity.builder().id(2).build();

        Commit commit1 = Commit.builder()
                .id(1)
                .versionDate(Timestamp.valueOf("2022-01-01 00:00:00"))
                .version("123")
                .testSets(new HashMap<>())
                .build();
        Commit commit2 = Commit.builder()
                .id(2)
                .versionDate(Timestamp.valueOf("2022-02-01 00:00:00"))
                .version("456")
                .testSets(new HashMap<>())
                .build();
        Commit commit3 = Commit.builder()
                .id(3)
                .versionDate(Timestamp.valueOf("2022-03-01 00:00:00"))
                .version("789")
                .testSets(new HashMap<>())
                .build();

        TreeMap<Commit, Integer> branch1Results = new TreeMap<>();
        branch1Results.put(commit1, 0);
        branch1Results.put(commit2, 1);

        TreeMap<Commit, Integer> branch2Results = new TreeMap<>();
        branch2Results.put(commit3, 1);

        when(commitService.getResultsForAllCommitsInBranchOrdered(any(GetResultsForTestSetsOfAllCommitsRequest.class)))
                .thenReturn(GetResultsForAllCommitsOfBranchWithIdResponse.builder().results(branch1Results).build())
                .thenReturn(GetResultsForAllCommitsOfBranchWithIdResponse.builder().results(branch2Results).build());
        when(branchDal.findAllPublicBranches())
                .thenReturn(List.of(branchEntity1, branchEntity2));

        // Execute the method under test
        Map<Integer, Integer> results = branchService.getBranchResults();

        // Verify the expected behavior and results
        assertEquals(2, results.size());
        assertEquals(1, results.get(1).intValue());
        assertEquals(1, results.get(2).intValue());

        // Verify the interactions with the mock objects
        verify(commitService, times(2)).getResultsForAllCommitsInBranchOrdered(any(GetResultsForTestSetsOfAllCommitsRequest.class));
    }
    @Test
    public void testGetBranchResultsForAuthed() {
        // Prepare test data
        BranchEntity branchEntity1 = BranchEntity.builder().id(1).build();
        BranchEntity branchEntity2 = BranchEntity.builder().id(2).build();

        Commit commit1 = Commit.builder()
                .id(1)
                .versionDate(Timestamp.valueOf("2022-01-01 00:00:00"))
                .version("123")
                .testSets(new HashMap<>())
                .build();
        Commit commit2 = Commit.builder()
                .id(2)
                .versionDate(Timestamp.valueOf("2022-02-01 00:00:00"))
                .version("456")
                .testSets(new HashMap<>())
                .build();
        Commit commit3 = Commit.builder()
                .id(3)
                .versionDate(Timestamp.valueOf("2022-03-01 00:00:00"))
                .version("789")
                .testSets(new HashMap<>())
                .build();

        TreeMap<Commit, Integer> branch1Results = new TreeMap<>();
        branch1Results.put(commit1, 0);
        branch1Results.put(commit2, 1);

        TreeMap<Commit, Integer> branch2Results = new TreeMap<>();
        branch2Results.put(commit3, 1);

        when(commitService.getResultsForAllCommitsInBranchOrdered(any(GetResultsForTestSetsOfAllCommitsRequest.class)))
                .thenReturn(GetResultsForAllCommitsOfBranchWithIdResponse.builder().results(branch1Results).build())
                .thenReturn(GetResultsForAllCommitsOfBranchWithIdResponse.builder().results(branch2Results).build());
        when(branchDal.findAllBranchesForUserWithId(1))
                .thenReturn(List.of(branchEntity1, branchEntity2));

        // Execute the method under test
        Map<Integer, Integer> results = branchService.getBranchResultsForAuthed(1);

        // Verify the expected behavior and results
        assertEquals(2, results.size());
        assertEquals(1, results.get(1).intValue());
        assertEquals(1, results.get(2).intValue());

        // Verify the interactions with the mock objects
        verify(commitService, times(2)).getResultsForAllCommitsInBranchOrdered(any(GetResultsForTestSetsOfAllCommitsRequest.class));
    }
    @Test
    public void testGetLastVersionOfBranches() {
        // Prepare test data
        BranchEntity branchEntity1 = BranchEntity.builder().id(1).build();
        BranchEntity branchEntity2 = BranchEntity.builder().id(2).build();

        Commit commit1 = Commit.builder()
                .id(1)
                .versionDate(Timestamp.valueOf("2022-01-01 00:00:00"))
                .version("123")
                .testSets(new HashMap<>())
                .build();
        Commit commit2 = Commit.builder()
                .id(2)
                .versionDate(Timestamp.valueOf("2022-02-01 00:00:00"))
                .version("456")
                .testSets(new HashMap<>())
                .build();
        Commit commit3 = Commit.builder()
                .id(3)
                .versionDate(Timestamp.valueOf("2022-03-01 00:00:00"))
                .version("789")
                .testSets(new HashMap<>())
                .build();

        TreeMap<Commit, Integer> branch1Results = new TreeMap<>();
        branch1Results.put(commit1, 0);
        branch1Results.put(commit2, 1);

        TreeMap<Commit, Integer> branch2Results = new TreeMap<>();
        branch2Results.put(commit3, 1);

        when(commitService.getResultsForAllCommitsInBranchOrdered(any(GetResultsForTestSetsOfAllCommitsRequest.class)))
                .thenReturn(GetResultsForAllCommitsOfBranchWithIdResponse.builder().results(branch1Results).build())
                .thenReturn(GetResultsForAllCommitsOfBranchWithIdResponse.builder().results(branch2Results).build());
        when(branchDal.findAllPublicBranches())
                .thenReturn(List.of(branchEntity1, branchEntity2));

        // Execute the method under test
        Map<Integer, String> results = branchService.getLastVersionOfBranches();

        // Verify the expected behavior and results
        assertEquals(2, results.size());
        assertEquals("456", results.get(1));
        assertEquals("789", results.get(2));

        // Verify the interactions with the mock objects
        verify(commitService, times(2)).getResultsForAllCommitsInBranchOrdered(any(GetResultsForTestSetsOfAllCommitsRequest.class));
    }
    @Test
    public void testGetLastVersionOfBranchesAuthed() {
        // Prepare test data
        BranchEntity branchEntity1 = BranchEntity.builder().id(1).build();
        BranchEntity branchEntity2 = BranchEntity.builder().id(2).build();

        Commit commit1 = Commit.builder()
                .id(1)
                .versionDate(Timestamp.valueOf("2022-01-01 00:00:00"))
                .version("123")
                .testSets(new HashMap<>())
                .build();
        Commit commit2 = Commit.builder()
                .id(2)
                .versionDate(Timestamp.valueOf("2022-02-01 00:00:00"))
                .version("456")
                .testSets(new HashMap<>())
                .build();
        Commit commit3 = Commit.builder()
                .id(3)
                .versionDate(Timestamp.valueOf("2022-03-01 00:00:00"))
                .version("789")
                .testSets(new HashMap<>())
                .build();

        TreeMap<Commit, Integer> branch1Results = new TreeMap<>();
        branch1Results.put(commit1, 0);
        branch1Results.put(commit2, 1);

        TreeMap<Commit, Integer> branch2Results = new TreeMap<>();
        branch2Results.put(commit3, 1);

        when(commitService.getResultsForAllCommitsInBranchOrdered(any(GetResultsForTestSetsOfAllCommitsRequest.class)))
                .thenReturn(GetResultsForAllCommitsOfBranchWithIdResponse.builder().results(branch1Results).build())
                .thenReturn(GetResultsForAllCommitsOfBranchWithIdResponse.builder().results(branch2Results).build());
        when(branchDal.findAllBranchesForUserWithId(1))
                .thenReturn(List.of(branchEntity1, branchEntity2));

        // Execute the method under test
        Map<Integer, String> results = branchService.getLastVersionOfBranchesAuthed(1);

        // Verify the expected behavior and results
        assertEquals(2, results.size());
        assertEquals("456", results.get(1));
        assertEquals("789", results.get(2));

        // Verify the interactions with the mock objects
        verify(commitService, times(2)).getResultsForAllCommitsInBranchOrdered(any(GetResultsForTestSetsOfAllCommitsRequest.class));
    }
    @Test
    public void testGetStats() {
        // Prepare test data
        BranchEntity branchEntity1 = BranchEntity.builder().id(1).build();
        BranchEntity branchEntity2 = BranchEntity.builder().id(2).build();

        Commit commit1 = Commit.builder()
                .id(1)
                .versionDate(Timestamp.valueOf("2022-01-01 00:00:00"))
                .version("123")
                .testSets(new HashMap<>())
                .build();
        Commit commit2 = Commit.builder()
                .id(2)
                .versionDate(Timestamp.valueOf("2022-02-01 00:00:00"))
                .version("456")
                .testSets(new HashMap<>())
                .build();
        Commit commit3 = Commit.builder()
                .id(3)
                .versionDate(Timestamp.valueOf("2022-03-01 00:00:00"))
                .version("789")
                .testSets(new HashMap<>())
                .build();

        TreeMap<Commit, Integer> branch1Results = new TreeMap<>();
        branch1Results.put(commit1, 0);
        branch1Results.put(commit2, 1);

        TreeMap<Commit, Integer> branch2Results = new TreeMap<>();
        branch2Results.put(commit3, 1);

        when(commitService.getResultsForAllCommitsInBranchOrdered(any(GetResultsForTestSetsOfAllCommitsRequest.class)))
                .thenReturn(GetResultsForAllCommitsOfBranchWithIdResponse.builder().results(branch1Results).build())
                .thenReturn(GetResultsForAllCommitsOfBranchWithIdResponse.builder().results(branch2Results).build());
        when(branchDal.findAllPublicBranches())
                .thenReturn(List.of(branchEntity1, branchEntity2));

        // Execute the method under test
        PieChartStats stats = branchService.getStats();

        // Verify the expected behavior and results
        assertEquals(3, stats.getTotal());
        assertEquals(2, stats.getPassed());

        // Verify the interactions with the mock objects
        verify(commitService, times(2)).getResultsForAllCommitsInBranchOrdered(any(GetResultsForTestSetsOfAllCommitsRequest.class));
    }
    @Test
    public void testGetStatsAuthed() {
        // Prepare test data
        BranchEntity branchEntity1 = BranchEntity.builder().id(1).build();
        BranchEntity branchEntity2 = BranchEntity.builder().id(2).build();

        Commit commit1 = Commit.builder()
                .id(1)
                .versionDate(Timestamp.valueOf("2022-01-01 00:00:00"))
                .version("123")
                .testSets(new HashMap<>())
                .build();
        Commit commit2 = Commit.builder()
                .id(2)
                .versionDate(Timestamp.valueOf("2022-02-01 00:00:00"))
                .version("456")
                .testSets(new HashMap<>())
                .build();
        Commit commit3 = Commit.builder()
                .id(3)
                .versionDate(Timestamp.valueOf("2022-03-01 00:00:00"))
                .version("789")
                .testSets(new HashMap<>())
                .build();

        TreeMap<Commit, Integer> branch1Results = new TreeMap<>();
        branch1Results.put(commit1, 0);
        branch1Results.put(commit2, 1);

        TreeMap<Commit, Integer> branch2Results = new TreeMap<>();
        branch2Results.put(commit3, 1);

        when(commitService.getResultsForAllCommitsInBranchOrdered(any(GetResultsForTestSetsOfAllCommitsRequest.class)))
                .thenReturn(GetResultsForAllCommitsOfBranchWithIdResponse.builder().results(branch1Results).build())
                .thenReturn(GetResultsForAllCommitsOfBranchWithIdResponse.builder().results(branch2Results).build());
        when(branchDal.findAllBranchesForUserWithId(1))
                .thenReturn(List.of(branchEntity1, branchEntity2));

        // Execute the method under test
        PieChartStats stats = branchService.getStatsAuthed(1);

        // Verify the expected behavior and results
        assertEquals(3, stats.getTotal());
        assertEquals(2, stats.getPassed());

        // Verify the interactions with the mock objects
        verify(commitService, times(2)).getResultsForAllCommitsInBranchOrdered(any(GetResultsForTestSetsOfAllCommitsRequest.class));
    }
}