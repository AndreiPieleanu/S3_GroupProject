package s3.algorithalliance.servicelayer.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import s3.algorithalliance.datalayer.ICommitDal;
import s3.algorithalliance.datalayer.entities.BranchEntity;
import s3.algorithalliance.datalayer.entities.CommitEntity;
import s3.algorithalliance.domain.Branch;
import s3.algorithalliance.domain.Commit;
import s3.algorithalliance.domain.TestSet;
import s3.algorithalliance.domain.reqresp.commit.*;
import s3.algorithalliance.domain.reqresp.testset.GetTestSetsRequest;
import s3.algorithalliance.domain.reqresp.testset.GetTestSetsResponse;
import s3.algorithalliance.servicelayer.ICommitService;
import s3.algorithalliance.servicelayer.ITestSetService;
import s3.algorithalliance.servicelayer.exc.CommitNotFoundException;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommitServiceTest {
    ICommitDal commitDal = mock(ICommitDal.class);
    ITestSetService testSetService = mock(ITestSetService.class);
    ICommitService commitService = new CommitService(commitDal, testSetService);
    private final CommitEntity existentCommit =
            CommitEntity.builder().id(1).name("added changes").originBranch(BranchEntity.builder().id(1).build()).version("12.34.56.78").build();
    private final Commit existentConvertedCommit =
            Commit.builder().id(1).name("added changes").originBranch(Branch.builder().id(1).build()).version("12.34.56.78").build();
    private final List<CommitEntity> commitsEntity = List.of(
            CommitEntity.builder().id(1).name("added changes 1").originBranch(BranchEntity.builder().id(1).build()).version("12.34.56.78").build(),
            CommitEntity.builder().id(2).name("added changes 2").originBranch(BranchEntity.builder().id(1).build()).version("12.34.56.79").build()
    );
    @Test
    public void getCommitShouldFind_WhenCommitExists(){
        // Arrange
        GetCommitRequest request = new GetCommitRequest(1);
        when(commitDal.findById(request.getCommitId())).thenReturn(Optional.of(existentCommit));
        Commit expectedCommit =
                Commit.builder().id(1).name("added changes").version("12.34.56.78").versionDate(existentCommit.getVersionDate()).originBranch(Branch.builder().id(1).build()).build();
        // Act 
        GetCommitResponse response = commitService.getCommit(request);
        // Assert 
        assertNotNull(response);
        assertEquals(expectedCommit, response.getCommit());
    }
    @Test
    public void getCommitShouldThrowException_WhenCommitDoesNotExist(){
        // Arrange 
        GetCommitRequest badRequest = new GetCommitRequest(2);
        when(commitDal.findById(badRequest.getCommitId())).thenReturn(Optional.empty());
        // Act 
        Throwable thrown = assertThrows(CommitNotFoundException.class,
                () -> commitService.getCommit(badRequest));
        // Assert
        assertNotNull(thrown);
        assertEquals(thrown.getMessage(), "404 NOT_FOUND \"COMMIT COULD NOT BE FOUND\"");
    }
    @Test
    public void getCommitsShouldReturnList_WhenCommitsExist(){
        // Arrange 
        GetCommitsRequest request = new GetCommitsRequest(1);
        when(commitDal.getAllEntitiesOfBranch(request.getBranchId())).thenReturn(commitsEntity);
        List<Commit> expectedCommits = List.of(
                Commit.builder().id(1).name("added changes 1").originBranch(Branch.builder().id(1).build()).version("12.34.56.78").build(),
                Commit.builder().id(2).name("added changes 2").originBranch(Branch.builder().id(1).build()).version("12.34.56.79").build()
        );
        // Act 
        GetCommitsResponse response = commitService.getAllCommitsOfBranch(request);
        // Assert 
        verify(commitDal).getAllEntitiesOfBranch(request.getBranchId());
        assertNotNull(response);
        assertEquals(expectedCommits, response.getCommits().values().stream().toList());
    }
    @Test
    public void createCommitShouldWork_WhenCommitIsNew(){
        // Arrange 
        CommitEntity commitToAdd = CommitEntity.builder().name("big " +
                "changes").originBranch(BranchEntity.builder().id(1).build()).testSets(new HashMap<>()).version("12.34.56.78").build();
        CommitEntity createdCommit = CommitEntity.builder().id(1).name("big " +
                "changes").originBranch(BranchEntity.builder().id(1).build()).testSets(new HashMap<>()).version("12.34.56.78").build();
        CreateCommitRequest request = CreateCommitRequest
                .builder()
                .name(commitToAdd.getName())
                .branchId(commitToAdd.getOriginBranch().getId())
                .version(commitToAdd.getVersion())
                .build();
        when(commitDal.save(any(CommitEntity.class))).thenReturn(createdCommit);
        // Act 
        CreateCommitResponse response = commitService.createCommit(request);
        // Assert 
        verify(commitDal).save(commitToAdd);
        assertNotNull(response);
        assertEquals(createdCommit.getId(), response.getCreatedId());
    }
    @Test
    public void GetTestSetsResultsInCommitWithIdShouldReturnMap_WhenCommitExists(){
        GetTestSetsResultsInCommitRequest request =
                new GetTestSetsResultsInCommitRequest(1);
        Object[] element1 = { 1, 1 };
        List<Object[]> list = new ArrayList<>();
        list.add(element1);
        when(commitDal.getResultsForTestSetsOfCommitWithId(request.getCommitId())).thenReturn(list);
        GetTestSetsResultsInCommitResponse response =
                commitService.getTestSetsResultsInCommitWithId((request));
        verify(commitDal).getResultsForTestSetsOfCommitWithId(request.getCommitId());
        assertNotNull(response);
        assertEquals(Map.of(1, 1), response.getResponse());
    }
    @Test
    public void getResultsForTestSetsShouldReturnList_WhenBranchExists(){
        GetResultsForTestSetsOfAllCommitsRequest request =
                new GetResultsForTestSetsOfAllCommitsRequest(1);
        Commit commitFound =
                Commit.builder().id(1).name("added changes").originBranch(Branch.builder().id(1).build()).version("12.34.56.78").build();
        Object[] element1 = { existentCommit, 1 };
        List<Object[]> list = new ArrayList<>();
        list.add(element1);
        when(commitDal.getResultsForAllCommitsInBranch(request.getBranchId())).thenReturn(list);
        GetResultsForTestSetsOfAllCommitsResponse response =
                commitService.getResultsForAllCommitsInBranch((request));
        verify(commitDal).getResultsForAllCommitsInBranch(request.getBranchId());
        assertNotNull(response);
        assertEquals(Map.of(commitFound, 1), response.getResults());
    }
    @Test
    public void getAllCommitsFromAllBranchesOfUserWithIdShouldReturnMap_WhenUserExists(){
        Integer userId = 1;
        when(commitDal.getAllCommitsPostedByUserWithId(userId)).thenReturn(
                List.of(existentCommit)
        );

        GetCommitsResponse response =
                commitService.getAllCommitsFromAllBranchesOfUserWithId(userId);
        
        verify(commitDal).getAllCommitsPostedByUserWithId(userId);
        assertNotNull(response);
        assertEquals(1, response.getCommits().size());
        assertEquals(Map.of(1, existentConvertedCommit), response.getCommits());
    }

    @Test
    void getCountCommitsOfBranchWithId() {
        GetCommitsRequest request = new GetCommitsRequest();
        request.setBranchId(1);

        int expectedResponse = 6;
        when(commitDal.getCountCommitsOfBranchWithId(1)).thenReturn(expectedResponse);

        int actualResponse = commitService.getCountCommitsOfBranchWithId(request);

        assertEquals(expectedResponse, actualResponse);
        verify(commitDal).getCountCommitsOfBranchWithId(1);
    }
    @Test
    public void testGetResultsForAllCommitsInBranch() {
        // Prepare test data
        int branchId = 1;

        CommitEntity commitEntity1 = CommitEntity.builder()
                .id(1)
                .versionDate(Timestamp.valueOf("2022-01-01 00:00:00"))
                .version("123")
                .testSets(new HashMap<>())
                .originBranch(BranchEntity.builder().id(1).build())
                .build();
        CommitEntity commitEntity2 = CommitEntity.builder()
                .id(2)
                .versionDate(Timestamp.valueOf("2022-02-01 00:00:00"))
                .version("456")
                .testSets(new HashMap<>())
                .originBranch(BranchEntity.builder().id(1).build())
                .build();

        Object[] result1 = {commitEntity1, 0};
        Object[] result2 = {commitEntity2, 1};

        List<Object[]> results = List.of(result1, result2);

        when(commitDal.getResultsForAllCommitsInBranch(branchId)).thenReturn(results);

        // Execute the method under test
        GetResultsForTestSetsOfAllCommitsResponse response =
                commitService.getResultsForAllCommitsInBranch(GetResultsForTestSetsOfAllCommitsRequest.builder().branchId(branchId).build());

        // Verify the expected behavior and results
        Map<Commit, Integer> resultMap = response.getResults();
        assertEquals(2, resultMap.size());
        assertEquals(1, resultMap.values().toArray()[0]);
        assertEquals(0, resultMap.values().toArray()[1]);

        // Verify the interactions with the mock object
        verify(commitDal).getResultsForAllCommitsInBranch(branchId);
    }
    @Test
    public void testGetResultsForAllCommitsInBranchOrdered() {
        // Prepare test data
        int branchId = 1;

        CommitEntity commitEntity1 = CommitEntity.builder()
                .id(1)
                .versionDate(Timestamp.valueOf("2022-01-01 00:00:00"))
                .version("123")
                .testSets(new HashMap<>())
                .originBranch(BranchEntity.builder().id(1).build())
                .build();
        CommitEntity commitEntity2 = CommitEntity.builder()
                .id(2)
                .versionDate(Timestamp.valueOf("2022-02-01 00:00:00"))
                .version("456")
                .testSets(new HashMap<>())
                .originBranch(BranchEntity.builder().id(1).build())
                .build();

        Object[] result1 = {commitEntity1, 0};
        Object[] result2 = {commitEntity2, 1};

        List<Object[]> results = List.of(result1, result2);

        when(commitDal.getResultsForAllCommitsInBranch(branchId)).thenReturn(results);

        // Execute the method under test
        GetResultsForAllCommitsOfBranchWithIdResponse response =
                commitService.getResultsForAllCommitsInBranchOrdered(GetResultsForTestSetsOfAllCommitsRequest.builder().branchId(branchId).build());

        // Verify the expected behavior and results
        Map<Commit, Integer> resultMap = response.getResults();
        assertEquals(2, resultMap.size());
        assertEquals(0, resultMap.values().toArray()[0]);
        assertEquals(1, resultMap.values().toArray()[1]);

        // Verify the interactions with the mock object
        verify(commitDal).getResultsForAllCommitsInBranch(branchId);
    }
    @Test
    public void testGetResultsForAllCommitsInBranchForReact() {
        // Prepare test data
        int branchId = 1;

        CommitEntity commitEntity1 = CommitEntity.builder()
                .id(1)
                .versionDate(Timestamp.valueOf("2022-01-01 00:00:00"))
                .version("123")
                .testSets(new HashMap<>())
                .originBranch(BranchEntity.builder().id(1).build())
                .build();
        CommitEntity commitEntity2 = CommitEntity.builder()
                .id(2)
                .versionDate(Timestamp.valueOf("2022-02-01 00:00:00"))
                .version("456")
                .testSets(new HashMap<>())
                .originBranch(BranchEntity.builder().id(1).build())
                .build();

        Object[] result1 = {commitEntity1, 0};
        Object[] result2 = {commitEntity2, 1};

        List<Object[]> results = List.of(result1, result2);

        when(commitDal.getResultsForAllCommitsInBranch(branchId)).thenReturn(results);

        // Execute the method under test
        Map<Integer, Integer> response = commitService.getResultsForAllCommitsInBranchForReact(GetResultsForTestSetsOfAllCommitsRequest.builder().branchId(branchId).build());

        // Verify the expected behavior and results
        assertEquals(2, response.size());
        assertEquals(0, response.get(commitEntity1.getId()));
        assertEquals(1, response.get(commitEntity2.getId()));

        // Verify the interactions with the mock object
        verify(commitDal).getResultsForAllCommitsInBranch(branchId);
    }
    @Test
    public void testGetCommitWithErrorOfBranchWithId() {
        // Prepare test data
        int branchId = 1;
        int errorId = 2;

        CommitEntity commitEntity1 = CommitEntity.builder().id(1).name("Commit 1").originBranch(BranchEntity.builder().id(1).build())
                .version("123").versionDate(new Timestamp(2022, 1, 1, 0, 0, 0, 0)).build();
        CommitEntity commitEntity2 = CommitEntity.builder().id(2).name("Commit 2").originBranch(BranchEntity.builder().id(1).build())
                .version("456").versionDate(new Timestamp(2022, 2, 1, 0, 0, 0, 0)).build();

        Object[] result1 = {commitEntity1.getId(), commitEntity1.getName(), commitEntity1.getOriginBranch().getId(),
                commitEntity1.getVersion(), commitEntity1.getVersionDate()};
        Object[] result2 = {commitEntity2.getId(), commitEntity2.getName(), commitEntity2.getOriginBranch().getId(),
                commitEntity2.getVersion(), commitEntity2.getVersionDate()};

        List<Object[]> results = List.of(result1, result2);

        when(commitDal.getCommitWithErrorOfBranchWithId(branchId, errorId)).thenReturn(results);

        // Execute the method under test
        GetCommitsResponse response = commitService.getCommitWithErrorOfBranchWithId(
                GetCommitsWithTestErrorsInBranchRequest.builder().branchId(branchId).errorId(errorId).build());

        // Verify the expected behavior and results
        Map<Integer, Commit> commits = response.getCommits();
        assertEquals(2, commits.size());

        Commit commit1 = commits.get(commitEntity1.getId());
        assertNotNull(commit1);
        assertEquals(commitEntity1.getId(), commit1.getId());
        assertEquals(commitEntity1.getName(), commit1.getName());
        assertEquals(commitEntity1.getOriginBranch().getId(), commit1.getOriginBranch().getId());
        assertEquals(commitEntity1.getVersion(), commit1.getVersion());
        assertEquals(commitEntity1.getVersionDate(), commit1.getVersionDate());

        Commit commit2 = commits.get(commitEntity2.getId());
        assertNotNull(commit2);
        assertEquals(commitEntity2.getId(), commit2.getId());
        assertEquals(commitEntity2.getName(), commit2.getName());
        assertEquals(commitEntity2.getOriginBranch().getId(), commit2.getOriginBranch().getId());
        assertEquals(commitEntity2.getVersion(), commit2.getVersion());
        assertEquals(commitEntity2.getVersionDate(), commit2.getVersionDate());

        // Verify the interactions with the mock object
        verify(commitDal).getCommitWithErrorOfBranchWithId(branchId, errorId);
    }
    @Test
    public void testGetCommitsWithTestResultInBranchRequest() {
        // Prepare test data
        int resultId = 1;
        int branchId = 2;

        CommitEntity commitEntity1 = CommitEntity.builder().id(1).name("Commit 1").originBranch(BranchEntity.builder().id(2).build())
                .version("123").versionDate(new Timestamp(2022, 1, 1, 0, 0, 0, 0)).build();
        CommitEntity commitEntity2 = CommitEntity.builder().id(2).name("Commit 2").originBranch(BranchEntity.builder().id(2).build())
                .version("456").versionDate(new Timestamp(2022, 2, 1, 0, 0, 0, 0)).build();

        Object[] result1 = {commitEntity1.getId(), commitEntity1.getName(), commitEntity1.getOriginBranch().getId(),
                commitEntity1.getVersion(), commitEntity1.getVersionDate()};
        Object[] result2 = {commitEntity2.getId(), commitEntity2.getName(), commitEntity2.getOriginBranch().getId(),
                commitEntity2.getVersion(), commitEntity2.getVersionDate()};

        List<Object[]> results = List.of(result1, result2);

        when(commitDal.getCommitsWithTestResultInBranchRequest(resultId, branchId)).thenReturn(results);

        // Execute the method under test
        GetCommitsResponse response = commitService.getCommitsWithTestResultInBranchRequest(
                GetCommitsWithTestResultInBranchRequest.builder().resultId(resultId).branchId(branchId).build());

        // Verify the expected behavior and results
        Map<Integer, Commit> commits = response.getCommits();
        assertEquals(2, commits.size());

        Commit commit1 = commits.get(commitEntity1.getId());
        assertNotNull(commit1);
        assertEquals(commitEntity1.getId(), commit1.getId());
        assertEquals(commitEntity1.getName(), commit1.getName());
        assertEquals(commitEntity1.getOriginBranch().getId(), commit1.getOriginBranch().getId());
        assertEquals(commitEntity1.getVersion(), commit1.getVersion());
        assertEquals(commitEntity1.getVersionDate(), commit1.getVersionDate());

        Commit commit2 = commits.get(commitEntity2.getId());
        assertNotNull(commit2);
        assertEquals(commitEntity2.getId(), commit2.getId());
        assertEquals(commitEntity2.getName(), commit2.getName());
        assertEquals(commitEntity2.getOriginBranch().getId(), commit2.getOriginBranch().getId());
        assertEquals(commitEntity2.getVersion(), commit2.getVersion());
        assertEquals(commitEntity2.getVersionDate(), commit2.getVersionDate());

        // Verify the interactions with the mock object
        verify(commitDal).getCommitsWithTestResultInBranchRequest(resultId, branchId);
    }
    @Test
    public void testGetCommitWithVersionOfBranchWithId() {
        // Prepare test data
        int branchId = 1;
        String version = "123";

        CommitEntity commitEntity1 = CommitEntity.builder().id(1).name("Commit 1").originBranch(BranchEntity.builder().id(1).build())
                .version("123").versionDate(new Timestamp(2022, 1, 1, 0, 0, 0, 0)).build();

        Object[] result = {commitEntity1.getId(), commitEntity1.getName(), commitEntity1.getOriginBranch().getId(),
                commitEntity1.getVersion(), commitEntity1.getVersionDate()};

        List<Object[]> results = new ArrayList<>();
        results.add(result);

        when(commitDal.getCommitWithVersionOfBranchWithId(branchId, version)).thenReturn(results);

        // Execute the method under test
        GetCommitsResponse response = commitService.getCommitWithVersionOfBranchWithId(
                GetCommitsWithVersionInBranchRequest.builder().branchId(branchId).version(version).build());

        // Verify the expected behavior and results
        Map<Integer, Commit> commits = response.getCommits();
        assertEquals(1, commits.size());

        Commit commit1 = commits.get(commitEntity1.getId());
        assertNotNull(commit1);
        assertEquals(commitEntity1.getId(), commit1.getId());
        assertEquals(commitEntity1.getName(), commit1.getName());
        assertEquals(commitEntity1.getOriginBranch().getId(), commit1.getOriginBranch().getId());
        assertEquals(commitEntity1.getVersion(), commit1.getVersion());
        assertEquals(commitEntity1.getVersionDate(), commit1.getVersionDate());

        // Verify the interactions with the mock object
        verify(commitDal).getCommitWithVersionOfBranchWithId(branchId, version);
    }
    @Test
    public void testGetTestSetsNumberOfCommitsInBranchWithId() {
        // Prepare test data
        int branchId = 1;

        // Create a valid GetCommitsRequest with the branchId
        GetCommitsRequest commitsRequest = GetCommitsRequest.builder()
                .branchId(branchId)
                .build();

        CommitEntity commitEntity1 = CommitEntity.builder()
                .id(1)
                .originBranch(BranchEntity.builder().id(branchId).build()) // Set a valid BranchEntity
                .build();

        CommitEntity commitEntity2 = CommitEntity.builder()
                .id(2)
                .originBranch(BranchEntity.builder().id(branchId).build()) // Set a valid BranchEntity
                .build();

        List<CommitEntity> commitEntities = List.of(commitEntity1, commitEntity2);

        // Mock the behavior of commitDal.getAllEntitiesOfBranch() to return the commitEntities
        when(commitDal.getAllEntitiesOfBranch(branchId)).thenReturn(commitEntities);

        TestSet testSet1 = TestSet.builder().id(1).build();
        TestSet testSet2 = TestSet.builder().id(2).build();
        Map<Integer, TestSet> testSetsOfCommit = new HashMap<>();
        testSetsOfCommit.put(testSet1.getId(), testSet1);
        testSetsOfCommit.put(testSet2.getId(), testSet2);
        GetTestSetsResponse testSetsOfCommitResponse = new GetTestSetsResponse(testSetsOfCommit);

        when(testSetService.getTestSetsOfCommitWithId(any(GetTestSetsRequest.class))).thenReturn(testSetsOfCommitResponse);

        // Execute the method under test
        Map<Integer, Integer> counts = commitService.getTestSetsNumberOfCommitsInBranchWithId(branchId);

        // Verify the expected behavior and results
        assertEquals(2, counts.size());
        assertEquals(2, counts.get(commitEntity1.getId()).intValue());
        assertEquals(2, counts.get(commitEntity2.getId()).intValue());

        // Verify the interactions with the mock objects
        verify(commitDal).getAllEntitiesOfBranch(branchId);
        verify(testSetService, times(2)).getTestSetsOfCommitWithId(any(GetTestSetsRequest.class));
    }
}