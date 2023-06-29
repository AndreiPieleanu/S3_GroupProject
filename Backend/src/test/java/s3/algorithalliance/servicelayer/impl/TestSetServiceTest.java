package s3.algorithalliance.servicelayer.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import s3.algorithalliance.datalayer.ITestSetDal;
import s3.algorithalliance.datalayer.entities.CommitEntity;
import s3.algorithalliance.datalayer.entities.TestSetEntity;
import s3.algorithalliance.domain.Commit;
import s3.algorithalliance.domain.TestSet;
import s3.algorithalliance.domain.reqresp.testres.GetTestResultsRequest;
import s3.algorithalliance.domain.reqresp.testres.GetTestResultsResponse;
import s3.algorithalliance.domain.reqresp.testset.*;
import s3.algorithalliance.servicelayer.ITestService;
import s3.algorithalliance.servicelayer.ITestSetService;
import s3.algorithalliance.servicelayer.exc.TestSetNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestSetServiceTest {
    ITestSetDal testSetDal = mock(ITestSetDal.class);
    ITestService testService = mock(ITestService.class);
    ITestSetService testSetService = new TestSetService(testSetDal, testService);
    private final TestSetEntity existentTestSet =
            TestSetEntity.builder().id(1).name("Test set 1").commitEntity(CommitEntity.builder().id(1).build()).build();
    private final List<TestSetEntity> testSetsList = List.of(
            TestSetEntity.builder().id(1).name("added changes 1").commitEntity(CommitEntity.builder().id(1).build()).build(),
            TestSetEntity.builder().id(2).name("added changes 2").commitEntity(CommitEntity.builder().id(1).build()).build()
    );
    @Test
    public void getTestSetOfCommitWithId1ShouldReturn_WhenTestSetExists(){
        // Arrange
        GetTestSetRequest request = new GetTestSetRequest(1);
        when(testSetDal.findById(request.getTestSetId())).thenReturn(Optional.of(existentTestSet));
        TestSet expectedTestSet =
                TestSet.builder().id(1).name("Test set 1").commit(Commit.builder().id(1).build()).build();
        // Act 
        GetTestSetResponse response = testSetService.getTestSet(request);
        // Assert 
        verify(testSetDal).findById(request.getTestSetId());
        assertNotNull(response);
        assertEquals(expectedTestSet, response.getTestSet());
    }
    @Test
    public void getTestSetShouldThrowException_WhenTestSetDoesNotExist(){
        // Arrange 
        GetTestSetRequest badRequest = new GetTestSetRequest(55);
        when(testSetDal.findById(badRequest.getTestSetId())).thenReturn(Optional.empty());
        // Act 
        Throwable thrown =
                assertThrows(TestSetNotFoundException.class, () -> testSetService.getTestSet(badRequest));
        // Assert 
        assertNotNull(thrown);
        assertEquals("404 NOT_FOUND \"TEST SET COULD NOT BE FOUND\"", thrown.getMessage());
    }
    @Test
    public void getTestSetsShouldReturnList_WhenTestSetsExist(){
        // Arrange 
        GetTestSetsRequest request = new GetTestSetsRequest(1);
        when(testSetDal.getAllTestSetsOfCommitWithId(request.getCommitId())).thenReturn(testSetsList);
        List<TestSet> expectedList = List.of(
                TestSet.builder().id(1).name("added changes 1").commit(Commit.builder().id(1).build()).build(),
                TestSet.builder().id(2).name("added changes 2").commit(Commit.builder().id(1).build()).build()
        );
        // Act 
        GetTestSetsResponse response =
                testSetService.getTestSetsOfCommitWithId(request);
        // Assert 
        verify(testSetDal).getAllTestSetsOfCommitWithId(request.getCommitId());
        assertNotNull(response);
        assertEquals(expectedList, response.getTestSets().values().stream().toList());
    }
    @Test
    public void createTestSetShouldCreate_WhenTestSetIsNew(){
        // Arrange 
        TestSetEntity testSetToAdd = TestSetEntity
                .builder()
                .name("test set 1")
                .testResults(new HashMap<>())
                .commitEntity(CommitEntity.builder().id(1).build())
                .build();
        TestSetEntity createdTestSet = TestSetEntity
                .builder()
                .id(1)
                .name("test set 1")
                .testResults(new HashMap<>())
                .commitEntity(CommitEntity.builder().id(1).build())
                .build();
        CreateTestSetRequest request = CreateTestSetRequest
                .builder()
                .name(testSetToAdd.getName())
                .commitId(testSetToAdd.getCommitEntity().getId())
                .build();
        when(testSetDal.save(testSetToAdd)).thenReturn(createdTestSet);
        // Act 
        CreateTestSetResponse response = testSetService.createTestSet(request);
        // Assert 
        verify(testSetDal).save(testSetToAdd);
        assertNotNull(response);
        assertEquals(createdTestSet.getId(), response.getCreatedId());
    }
    @Test
    public void getNumberOfTestsInTestSetShouldReturn_WhenTestSetExists(){
        // Arrange
        GetTestCountRequest request = new GetTestCountRequest(1);
        when(testSetDal.getCountOfAllTestResultsOfTestSetWithId(request.getTestSetId())).thenReturn(1L);
        // Act
        GetTestCountResponse response =
                testSetService.getCountOfAllTestResultsOfTestSetWithId(request);
        // Assert
        verify(testSetDal).getCountOfAllTestResultsOfTestSetWithId(request.getTestSetId());
        assertNotNull(response);
        assertEquals(1, response.getTestCount());
    }
    @Test
    public void getNumberOfPassedTestsInTestSetShouldReturn_WhenTestSetExists(){
        // Arrange
        GetTestCountRequest request = new GetTestCountRequest(1);
        when(testSetDal.getNumberOfSuccessfulTestsInTestSetWithId(request.getTestSetId())).thenReturn(1L);
        // Act
        GetTestCountResponse response =
                testSetService.getCountOfSuccessfulTestsOfTestSetWithId(request);
        // Assert
        verify(testSetDal).getNumberOfSuccessfulTestsInTestSetWithId(request.getTestSetId());
        assertNotNull(response);
        assertEquals(1, response.getTestCount());
    }
    @Test
    public void GetTestResultsShouldReturnMap_WhenTestSetExists(){
        // Arrange
        GetTestResultsInTestSetRequest request = new GetTestResultsInTestSetRequest(1);
        Object[] element1 = { 1, 1 };
        List<Object[]> list = new ArrayList<>();
        list.add(element1);
        when(testSetDal.getTestsOfTestStepAndTheirResults(request.getTestSetId())).thenReturn(list);
        // Act
        GetTestResultsInTestSetResponse response =
                testSetService.getTestResultsInTestSetWithId(request);
        // Assert
        verify(testSetDal).getTestsOfTestStepAndTheirResults(request.getTestSetId());
        assertNotNull(response);
        assertEquals(Map.of(1, 1), response.getResponse());
    }
    @Test
    public void testGetTestSetsWithErrorOfCommit() {
        // Prepare test data
        int commitId = 1;
        int errorId = 2;

        TestSetEntity testSetEntity1 = TestSetEntity.builder().id(1).name("Test Set 1")
                .commitEntity(CommitEntity.builder().id(1).build()).build();

        Object[] result = {testSetEntity1.getId(), testSetEntity1.getName(), testSetEntity1.getCommitEntity().getId()};

        List<Object[]> results = new ArrayList<>();
        results.add(result);

        when(testSetDal.getTestSetsWithErrorOfCommit(commitId, errorId)).thenReturn(results);

        // Execute the method under test
        GetTestSetsResponse response = testSetService.getTestSetsWithErrorOfCommit(
                GetTestSetsWithTestErrorsInCommitRequest.builder().commitId(commitId).errorId(errorId).build());

        // Verify the expected behavior and results
        Map<Integer, TestSet> testSets = response.getTestSets();
        assertEquals(1, testSets.size());

        TestSet testSet1 = testSets.get(testSetEntity1.getId());
        assertNotNull(testSet1);
        assertEquals(testSetEntity1.getId(), testSet1.getId());
        assertEquals(testSetEntity1.getName(), testSet1.getName());
        assertEquals(testSetEntity1.getCommitEntity().getId(), testSet1.getCommit().getId());

        // Verify the interactions with the mock object
        verify(testSetDal).getTestSetsWithErrorOfCommit(commitId, errorId);
    }
    @Test
    public void testGetTestSetsWithResultsOfCommit() {
        // Prepare test data
        int commitId = 1;
        int resultId = 2;

        TestSetEntity testSetEntity1 = TestSetEntity.builder().id(1).name("Test Set 1")
                .commitEntity(CommitEntity.builder().id(1).build()).build();

        Object[] result = {testSetEntity1.getId(), testSetEntity1.getName(), testSetEntity1.getCommitEntity().getId()};

        List<Object[]> results = new ArrayList<>();
        results.add(result);

        when(testSetDal.getTestSetsWithResultsOfCommit(commitId, resultId)).thenReturn(results);

        // Execute the method under test
        GetTestSetsResponse response = testSetService.getTestSetsWithResultsOfCommit(
                GetTestSetsWithTestResultsInCommitRequest.builder().commitId(commitId).resultId(resultId).build());

        // Verify the expected behavior and results
        Map<Integer, TestSet> testSets = response.getTestSets();
        assertEquals(1, testSets.size());

        TestSet testSet1 = testSets.get(testSetEntity1.getId());
        assertNotNull(testSet1);
        assertEquals(testSetEntity1.getId(), testSet1.getId());
        assertEquals(testSetEntity1.getName(), testSet1.getName());
        assertEquals(testSetEntity1.getCommitEntity().getId(), testSet1.getCommit().getId());

        // Verify the interactions with the mock object
        verify(testSetDal).getTestSetsWithResultsOfCommit(commitId, resultId);
    }


    @Test
    void getCountTestSetsOfCommitWithId() {
        GetTestResultsRequest request = new GetTestResultsRequest();
        request.setTestSetId(1);

        int expectedResponse = 6;
        when(testSetDal.getCountTestSetsOfCommitWithId(1)).thenReturn(expectedResponse);

        int actualResponse = testSetDal.getCountTestSetsOfCommitWithId(request.getTestSetId());

        assertEquals(expectedResponse, actualResponse);
        verify(testSetDal).getCountTestSetsOfCommitWithId(1);
    }
    @Test
    public void testGetCountTestSetsOfCommitWithId() {
        // Prepare test data
        int commitId = 1;
        GetTestSetsRequest request = GetTestSetsRequest.builder().commitId(commitId).build();
        int expectedCount = 5;

        // Mock the behavior of testSetDal.getCountTestSetsOfCommitWithId() to return the expected count
        when(testSetDal.getCountTestSetsOfCommitWithId(commitId)).thenReturn(expectedCount);

        // Execute the method under test
        int result = testSetService.getCountTestSetsOfCommitWithId(request);

        // Verify the expected behavior and results
        assertEquals(expectedCount, result);

        // Verify the interaction with the mock object
        verify(testSetDal).getCountTestSetsOfCommitWithId(commitId);
    }
}