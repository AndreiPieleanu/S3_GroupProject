package s3.algorithalliance.servicelayer.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import s3.algorithalliance.datalayer.ISubTestDal;
import s3.algorithalliance.datalayer.entities.SubtestEntity;
import s3.algorithalliance.datalayer.entities.TestEntity;
import s3.algorithalliance.domain.SubTest;
import s3.algorithalliance.domain.reqresp.commit.GetCommitsRequest;
import s3.algorithalliance.domain.reqresp.subtest.*;
import s3.algorithalliance.servicelayer.ISubtestService;
import s3.algorithalliance.servicelayer.ITestStepService;
import s3.algorithalliance.servicelayer.exc.SubtestNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubtestServiceTest {
    ISubTestDal subTestDal = mock(ISubTestDal.class);
    ITestStepService testStepService = mock(ITestStepService.class);
    ISubtestService subtestService = new SubtestService(subTestDal, testStepService);
    private final SubtestEntity existentSubTest =
            SubtestEntity.builder().id(1).name("Subtest 1").testResult(TestEntity.builder().id(1).build()).build();
    private final List<SubtestEntity> subtestsList = List.of(
            SubtestEntity.builder().id(1).name("Subtest 1").testResult(TestEntity.builder().id(1).build()).build(),
            SubtestEntity.builder().id(2).name("Subtest 2").testResult(TestEntity.builder().id(1).build()).build()
    );
    @Test
    public void getSubtestsOfTestShouldReturnList_WhenSubtestsExist(){
        // Arrange
        GetSubtestsRequest request = new GetSubtestsRequest(1);
        when(subTestDal.getAllSubtestsOfTestResultWithId(request.getTestResultId())).thenReturn(subtestsList);
        List<SubTest> expectedList = List.of(
                SubTest.builder().id(1).name("Subtest 1").test(s3.algorithalliance.domain.Test.builder().id(1).build()).build(),
                SubTest.builder().id(2).name("Subtest 2").test(s3.algorithalliance.domain.Test.builder().id(1).build()).build()
        );
        // Act 
        GetSubtestsResponse response =
                subtestService.getSubtestsOfTestWithId(request);
        // Assert 
        verify(subTestDal).getAllSubtestsOfTestResultWithId(request.getTestResultId());
        assertNotNull(response);
        assertEquals(expectedList, response.getSubtests().values().stream().toList());
    }
    @Test
    public void getSubtestShouldReturnSubtest_WhenSubtestExists(){
        // Arrange
        GetSubtestRequest request = new GetSubtestRequest(1);
        when(subTestDal.findById(request.getSubtestId())).thenReturn(Optional.of(existentSubTest));
        SubTest expectedResult =
                SubTest.builder().id(1).name("Subtest 1").test(s3.algorithalliance.domain.Test.builder().id(1).build()).build();
        // Act 
        GetSubtestResponse response =
                subtestService.getSubtest(request);
        // Assert
        verify(subTestDal).findById(request.getSubtestId());
        assertNotNull(response);
        assertEquals(expectedResult, response.getSubTest());
    }
    @Test
    public void getSubtestShouldThrowException_WhenSubtestDoesNotExist(){
        // Arrange 
        GetSubtestRequest badRequest = new GetSubtestRequest(55);
        when(subTestDal.findById(badRequest.getSubtestId())).thenReturn(Optional.empty());
        // Act 
        Throwable thrown = assertThrows(SubtestNotFoundException.class,
                () -> subtestService.getSubtest(badRequest));
        // Assert 
        assertNotNull(thrown);
        assertEquals("404 NOT_FOUND \"SUBTEST COULD NOT BE FOUND\"", thrown.getMessage());
    }
    @Test
    public void createSubtestShouldCreate_WhenSubtestIsNew(){
        // Arrange
        SubtestEntity subtestToAdd =
                SubtestEntity.builder().name("Subtest 1").testResult(TestEntity.builder().id(1).build()).testSteps(new HashMap<>()).build();
        SubtestEntity addedSubtest =
                SubtestEntity.builder().id(1).name("Subtest 1").testResult(TestEntity.builder().id(1).build()).testSteps(new HashMap<>()).build();
        CreateSubtestRequest request =
                CreateSubtestRequest
                        .builder()
                        .name(subtestToAdd.getName())
                        .testId(subtestToAdd.getTestResult().getId())
                        .build();
        when(subTestDal.save(subtestToAdd)).thenReturn(addedSubtest);
        // Act
        CreateSubtestResponse response =
                subtestService.createSubtest(request);
        // Assert 
        verify(subTestDal).save(subtestToAdd);
        assertNotNull(response);
        assertEquals(response.getId(), response.getId());
    }
    @Test
    public void getNumberOfTestStepsInSubtestShouldReturn_WhenSubtestExists(){
        // Arrange
        GetTestStepsCountRequest request = new GetTestStepsCountRequest(1);
        when(subTestDal.getNumberOfTestStepsFromSubtestWithId(request.getSubtestId())).thenReturn(1L);
        // Act
        GetTestStepsCountResponse response =
                subtestService.getTestStepsCountOfSubtestWithId(request);
        // Assert
        verify(subTestDal).getNumberOfTestStepsFromSubtestWithId(request.getSubtestId());
        assertNotNull(response);
        assertEquals(1, response.getCount());
    }
    @Test
    public void getNumberOfPassedTestStepsInSubtestShouldReturn_WhenSubtestExists(){
        // Arrange
        GetTestStepsCountRequest request = new GetTestStepsCountRequest(1);
        when(subTestDal.getNumberOfSuccessfulTestStepsForSubtestWithId(request.getSubtestId())).thenReturn(1L);
        // Act
        GetTestStepsCountResponse response =
                subtestService.getSuccessfulTestStepsOfSubtestWithId(request);
        // Assert
        verify(subTestDal).getNumberOfSuccessfulTestStepsForSubtestWithId(request.getSubtestId());
        assertNotNull(response);
        assertEquals(1, response.getCount());
    }
    @Test
    public void testGetSubTestsWithResultOfTest() {
        // Prepare test data
        int testId = 1;
        int resultId = 2;

        SubtestEntity subTestEntity1 = SubtestEntity.builder().id(1).name("SubTest 1")
                .testResult(TestEntity.builder().id(1).build()).build();

        Object[] result = {subTestEntity1.getId(), subTestEntity1.getName(), subTestEntity1.getTestResult().getId()};

        List<Object[]> results = new ArrayList<>();
        results.add(result);

        when(subTestDal.getSubTestsWithResultOfTest(testId, resultId)).thenReturn(results);

        // Execute the method under test
        GetSubtestsResponse response = subtestService.getSubTestsWithResultOfTest(
                GetSubTestWithTestResultsOfTestRequest.builder().testId(testId).resultId(resultId).build());

        // Verify the expected behavior and results
        Map<Integer, SubTest> subtests = response.getSubtests();
        assertEquals(1, subtests.size());

        SubTest subTest1 = subtests.get(subTestEntity1.getId());
        assertNotNull(subTest1);
        assertEquals(subTestEntity1.getId(), subTest1.getId());
        assertEquals(subTestEntity1.getName(), subTest1.getName());
        assertEquals(subTestEntity1.getTestResult().getId(), subTest1.getTest().getId());

        // Verify the interactions with the mock object
        verify(subTestDal).getSubTestsWithResultOfTest(testId, resultId);
    }
    @Test
    public void testGetSubTestsWithErrorOfTest() {
        // Prepare test data
        int testId = 1;
        int errorId = 2;

        SubtestEntity subTestEntity1 = SubtestEntity.builder().id(1).name("SubTest 1")
                .testResult(TestEntity.builder().id(1).build()).build();

        Object[] result = {subTestEntity1.getId(), subTestEntity1.getName(), subTestEntity1.getTestResult().getId()};

        List<Object[]> results = new ArrayList<>();
        results.add(result);

        when(subTestDal.getSubTestsWithErrorOfTest(testId, errorId)).thenReturn(results);

        // Execute the method under test
        GetSubtestsResponse response = subtestService.getSubTestsWithErrorOfTest(
                GetSUbTestWithTestErrorsOfTestRequest.builder().testId(testId).errorId(errorId).build());

        // Verify the expected behavior and results
        Map<Integer, SubTest> subtests = response.getSubtests();
        assertEquals(1, subtests.size());

        SubTest subTest1 = subtests.get(subTestEntity1.getId());
        assertNotNull(subTest1);
        assertEquals(subTestEntity1.getId(), subTest1.getId());
        assertEquals(subTestEntity1.getName(), subTest1.getName());
        assertEquals(subTestEntity1.getTestResult().getId(), subTest1.getTest().getId());

        // Verify the interactions with the mock object
        verify(subTestDal).getSubTestsWithErrorOfTest(testId, errorId);
    }

    @Test
    void getCountSubTestsOfTestWithId() {
        GetSubtestsRequest request = new GetSubtestsRequest();
        request.setTestResultId(1);

        int expectedResponse = 6;
        when(subTestDal.getCountSubTestsOfTestWithId(1)).thenReturn(expectedResponse);

        int actualResponse = subtestService.getCountSubTestsOfTestWithId(request);

        assertEquals(expectedResponse, actualResponse);
        verify(subTestDal).getCountSubTestsOfTestWithId(1);
    }
}