package s3.algorithalliance.servicelayer.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import s3.algorithalliance.datalayer.ITestDal;
import s3.algorithalliance.datalayer.entities.TestEntity;
import s3.algorithalliance.datalayer.entities.TestSetEntity;
import s3.algorithalliance.domain.TestSet;
import s3.algorithalliance.domain.reqresp.testres.*;
import s3.algorithalliance.domain.reqresp.testset.GetTestCountRequest;
import s3.algorithalliance.domain.reqresp.testset.GetTestCountResponse;
import s3.algorithalliance.domain.reqresp.testset.GetTestSetsRequest;
import s3.algorithalliance.servicelayer.ISubtestService;
import s3.algorithalliance.servicelayer.ITestService;
import s3.algorithalliance.servicelayer.exc.TestResultNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestResultServiceTest {
    ITestDal testDal = mock(ITestDal.class);
    ISubtestService subtestService = mock(ISubtestService.class);
    ITestService testResultService = new TestService(testDal, subtestService);
    private final TestEntity existentTestResult =
            TestEntity.builder().id(1).name("Test 1").testSet(TestSetEntity.builder().id(1).build()).build();
    private final List<TestEntity> testResultsList = List.of(
            TestEntity.builder().id(1).name("Test 1").testSet(TestSetEntity.builder().id(1).build()).build(),
            TestEntity.builder().id(2).name("Test 2").testSet(TestSetEntity.builder().id(1).build()).build()
    );
    @Test
    public void getTestsOfTestSetShouldReturnList_WhenTestsExist(){
        // Arrange
        GetTestResultsRequest request = new GetTestResultsRequest(1);
        when(testDal.getAllTestResultsOfTestSetWithId(request.getTestSetId())).thenReturn(testResultsList);
        List<s3.algorithalliance.domain.Test> expectedList = List.of(
                s3.algorithalliance.domain.Test.builder().id(1).name("Test 1").testSet(TestSet.builder().id(1).build()).build(),
                s3.algorithalliance.domain.Test.builder().id(2).name("Test 2").testSet(TestSet.builder().id(1).build()).build()
        );
        // Act 
        GetTestResultsResponse response =
                testResultService.getTestResultsOfTestSetWithId(request);
        // Assert 
        verify(testDal).getAllTestResultsOfTestSetWithId(request.getTestSetId());
        assertNotNull(response);
        assertEquals(expectedList, response.getTestResults().values().stream().toList());
    }
    @Test
    public void getTestShouldReturnTest_WhenTestExists(){
        // Arrange
        GetTestResultRequest request = new GetTestResultRequest(1);
        when(testDal.findById(request.getTestId())).thenReturn(Optional.of(existentTestResult));
        s3.algorithalliance.domain.Test expectedResult =
                s3.algorithalliance.domain.Test.builder().id(1).name("Test 1").testSet(TestSet.builder().id(1).build()).build();
        // Act 
        GetTestResultResponse response =
                testResultService.getTestResult(request);
        // Assert
        verify(testDal).findById(request.getTestId());
        assertNotNull(response);
        assertEquals(expectedResult, response.getTest());
    }
    @Test
    public void getTestShouldThrowException_WhenTestDoesNotExist(){
        // Arrange 
        GetTestResultRequest badRequest = new GetTestResultRequest(55);
        when(testDal.findById(badRequest.getTestId())).thenReturn(Optional.empty());
        // Act 
        Throwable thrown = assertThrows(TestResultNotFoundException.class,
                () -> testResultService.getTestResult(badRequest));
        // Assert 
        assertNotNull(thrown);
        assertEquals("404 NOT_FOUND \"TEST COULD NOT BE FOUND\"", thrown.getMessage());
    }
    @Test
    public void createTestShouldCreate_WhenTestIsNew(){
        // Arrange
        TestEntity testToAdd =
                TestEntity.builder().name("Test 1").testSet(TestSetEntity.builder().id(1).build()).duration("00:02:33").subtests(new HashMap<>()).build();
        TestEntity addedTest =
                TestEntity.builder().id(1).name("Test 1").testSet(TestSetEntity.builder().id(1).build()).duration("00:02:33").subtests(new HashMap<>()).build();
        CreateTestResultRequest request =
                CreateTestResultRequest
                        .builder()
                        .name(testToAdd.getName())
                        .testSetId(testToAdd.getTestSet().getId())
                        .duration(testToAdd.getDuration())
                        .build();
        when(testDal.save(testToAdd)).thenReturn(addedTest);
        // Act
        CreateTestResultResponse response =
                testResultService.createTestResult(request);
        // Assert 
        verify(testDal).save(testToAdd);
        assertNotNull(response);
        assertEquals(addedTest.getId(), response.getId());
    }
    @Test
    public void getNumberOfSubtestsInTestShouldReturn_WhenTestExists(){
        // Arrange
        GetSubtestsCountRequest request = new GetSubtestsCountRequest(1);
        when(testDal.getNumberOfSubtestsInTestWithId(request.getTestId())).thenReturn(1L);
        // Act
        GetSubtestsCountResponse response =
                testResultService.getSubtestsOfTestWithId(request);
        // Assert
        verify(testDal).getNumberOfSubtestsInTestWithId(request.getTestId());
        assertNotNull(response);
        assertEquals(1, response.getCount());
    }
    @Test
    public void getNumberOfPassedSubtestsInTestShouldReturn_WhenTestExists(){
        // Arrange
        GetSubtestsCountRequest request = new GetSubtestsCountRequest(1);
        when(testDal.getNumberOfSuccessfulSubtestsOfTestWithId(request.getTestId())).thenReturn(1L);
        // Act
        GetSubtestsCountResponse response =
                testResultService.getSuccessfulSubtestsOfTestWithId(request);
        // Assert
        verify(testDal).getNumberOfSuccessfulSubtestsOfTestWithId(request.getTestId());
        assertNotNull(response);
        assertEquals(1, response.getCount());
    }
    @Test
    public void getSubtestsResultsInTestShouldReturnList_WhenTestExists(){
        GetSubtestsResultsInTestRequest request = new GetSubtestsResultsInTestRequest(1);
        TestEntity testEntity1 = TestEntity.builder().id(1).name("Test 1")
                .testSet(TestSetEntity.builder().id(1).build()).build();
        Object[] result = {testEntity1.getId(), 1};
        List<Object[]> results = new ArrayList<>();
        results.add(result);
        when(testDal.getResultsForSubtestsForTestWithId(request.getTestId())).thenReturn(results);

        GetSubtestsResultsInTestResponse response = testResultService.getSubtestsResultsInTestWithId(request);

        assertNotNull(response);
        assertEquals(1, response.getResponse().size());
        assertEquals(1, response.getResponse().values().toArray()[0]);
    }
    @Test
    public void testGetTestsWithResultOfTestSet() {
        // Prepare test data
        int testSetId = 1;
        int resultId = 2;

        TestEntity testEntity1 = TestEntity.builder().id(1).name("Test 1")
                .testSet(TestSetEntity.builder().id(1).build()).build();

        Object[] result = {testEntity1.getId(), testEntity1.getName(), testEntity1.getTestSet().getId()};

        List<Object[]> results = new ArrayList<>();
        results.add(result);

        when(testDal.getTestsWithResultOfTestSet(testSetId, resultId)).thenReturn(results);

        // Execute the method under test
        GetTestResultsResponse response = testResultService.getTestsWithResultOfTestSet(
                GetTestWithTestResultsOfTestSetRequest.builder().testSetId(testSetId).resultId(resultId).build());

        // Verify the expected behavior and results
        Map<Integer, s3.algorithalliance.domain.Test> tests = response.getTestResults();
        assertEquals(1, tests.size());

        s3.algorithalliance.domain.Test test1 = tests.get(testEntity1.getId());
        assertNotNull(test1);
        assertEquals(testEntity1.getId(), test1.getId());
        assertEquals(testEntity1.getName(), test1.getName());
        assertEquals(testEntity1.getTestSet().getId(), test1.getTestSet().getId());

        // Verify the interactions with the mock object
        verify(testDal).getTestsWithResultOfTestSet(testSetId, resultId);
    }
    @Test
    public void testGetTestsWithErrorOfTestSet() {
        // Prepare test data
        int testSetId = 1;
        int errorId = 2;

        TestEntity testEntity1 = TestEntity.builder().id(1).name("Test 1")
                .testSet(TestSetEntity.builder().id(1).build()).build();

        Object[] result = {testEntity1.getId(), testEntity1.getName(), testEntity1.getTestSet().getId()};

        List<Object[]> results = new ArrayList<>();
        results.add(result);

        when(testDal.getTestsWithErrorOfTestSet(testSetId, errorId)).thenReturn(results);

        // Execute the method under test
        GetTestResultsResponse response = testResultService.getTestsWithErrorOfTestSet(
                GetTestWithTestErrorsOfTestSetRequest.builder().testSetId(testSetId).errorId(errorId).build());

        // Verify the expected behavior and results
        Map<Integer, s3.algorithalliance.domain.Test> tests = response.getTestResults();
        assertEquals(1, tests.size());

        s3.algorithalliance.domain.Test test1 = tests.get(testEntity1.getId());
        assertNotNull(test1);
        assertEquals(testEntity1.getId(), test1.getId());
        assertEquals(testEntity1.getName(), test1.getName());
        assertEquals(testEntity1.getTestSet().getId(), test1.getTestSet().getId());

        // Verify the interactions with the mock object
        verify(testDal).getTestsWithErrorOfTestSet(testSetId, errorId);
    }

    @Test
    void getCountTestsOfTestSetWithId() {
        GetTestResultsRequest request = new GetTestResultsRequest();
        request.setTestSetId(1);

        int expectedResponse = 6;
        when(testDal.getCountTestsOfTestSetWithId(1)).thenReturn(expectedResponse);

        int actualResponse = testResultService.getCountTestsOfTestSetWithId(request);

        assertEquals(expectedResponse, actualResponse);
        verify(testDal).getCountTestsOfTestSetWithId(1);
    }
}