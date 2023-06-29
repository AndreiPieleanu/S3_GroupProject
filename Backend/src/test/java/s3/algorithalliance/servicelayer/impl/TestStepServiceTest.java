package s3.algorithalliance.servicelayer.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import s3.algorithalliance.datalayer.ITestStepDal;
import s3.algorithalliance.datalayer.entities.*;
import s3.algorithalliance.domain.*;
import s3.algorithalliance.domain.reqresp.testres.GetTestResultsRequest;
import s3.algorithalliance.domain.reqresp.teststep.*;
import s3.algorithalliance.servicelayer.ITestStepService;
import s3.algorithalliance.servicelayer.exc.TestStepNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestStepServiceTest {
    ITestStepDal testStepDal = mock(ITestStepDal.class);
    ITestStepService testStepService = new TestStepService(testStepDal);
    private final TestErrorEntity testErrorEntity =
            TestErrorEntity.builder().id(1).errorCode("CMX").build();
    private final TestWarningEntity testWarningEntity =
            TestWarningEntity.builder().id(1).warningCode(1).build();
    private final ResultTypeEntity resultTypeEntity =
            ResultTypeEntity.builder().id(1).resultName("Code died").build();
    private final TestError testError =
            TestError.builder().id(1).errorCode("CMX").build();
    private final TestWarning testWarning =
            TestWarning.builder().id(1).warningCode(1).build();
    private final ResultType resultType =
            ResultType.builder().id(1).resultName("Code died").build();
    private final TestStepEntity existentTestStep =
            TestStepEntity.builder().id(1).stepNo(1).subtest(SubtestEntity.builder().id(1).build()).testError(testErrorEntity).testWarning(testWarningEntity).resultType(resultTypeEntity).build();
    private final List<TestStepEntity> testStepsList = List.of(
            TestStepEntity.builder().id(1).stepNo(1).subtest(SubtestEntity.builder().id(1).build()).testError(testErrorEntity).testWarning(testWarningEntity).resultType(resultTypeEntity).build(),
            TestStepEntity.builder().id(2).stepNo(2).subtest(SubtestEntity.builder().id(1).build()).testError(testErrorEntity).testWarning(testWarningEntity).resultType(resultTypeEntity).build()
    );
    @Test
    public void getTestStepsOfSubtestShouldReturnList_WhenTestStepsExist(){
        // Arrange
        GetTestStepsRequest request = new GetTestStepsRequest(1);
        when(testStepDal.getAllTestStepsOfSubtestWithId(request.getSubtestId())).thenReturn(testStepsList);
        List<TestStep> expectedList = List.of(
                TestStep.builder().id(1).stepNo(1).testError(testError).testWarning(testWarning).resultType(resultType).build(),
                TestStep.builder().id(2).stepNo(2).testError(testError).testWarning(testWarning).resultType(resultType).build()
        );
        // Act 
        GetTestStepsResponse response =
                testStepService.getAllTestStepsOfSubtestWithId(request);
        // Assert 
        verify(testStepDal).getAllTestStepsOfSubtestWithId(request.getSubtestId());
        assertNotNull(response);
        assertEquals(expectedList, response.getTestSteps().values().stream().toList());
    }
    @Test
    public void getTestStepShouldReturnTestStep_WhenTestStepExists(){
        // Arrange
        GetTestStepRequest request = new GetTestStepRequest(1);
        when(testStepDal.findById(request.getId())).thenReturn(Optional.of(existentTestStep));
        TestStep expectedResult = TestStep.builder().id(1).stepNo(1).testError(testError).testWarning(testWarning).resultType(resultType).build();
        // Act 
        GetTestStepResponse response =
                testStepService.getTestStep(request);
        // Assert
        verify(testStepDal).findById(request.getId());
        assertNotNull(response);
        assertEquals(expectedResult, response.getTestStep());
    }
    @Test
    public void getTestStepShouldThrowException_WhenTestStepDoesNotExist(){
        // Arrange 
        GetTestStepRequest badRequest = new GetTestStepRequest(55);
        when(testStepDal.findById(badRequest.getId())).thenReturn(Optional.empty());
        // Act 
        Throwable thrown = assertThrows(TestStepNotFoundException.class,
                () -> testStepService.getTestStep(badRequest));
        // Assert 
        assertNotNull(thrown);
        assertEquals("404 NOT_FOUND \"TEST STEP COULD NOT BE FOUND\"",
                thrown.getMessage());
    }
    @Test
    public void createTestStepShouldCreate_WhenTestStepIsNew(){
        // Arrange
        TestStepEntity testStepToAdd =
                TestStepEntity.builder().stepNo(1).result_details("Code died").subtest(SubtestEntity.builder().id(1).build()).testError(testErrorEntity).testWarning(testWarningEntity).resultType(resultTypeEntity).build();
        TestStepEntity addedTestStep =
                TestStepEntity.builder().id(1).stepNo(1).result_details("Code died").subtest(SubtestEntity.builder().id(1).build()).testError(testErrorEntity).testWarning(testWarningEntity).resultType(resultTypeEntity).build();
        CreateTestStepRequest request =
                CreateTestStepRequest
                        .builder()
                        .stepNo(testStepToAdd.getStepNo())
                        .resultDetails(testStepToAdd.getResult_details())
                        .resultTypeId(testStepToAdd.getResultType().getId())
                        .testWarningId(testStepToAdd.getTestWarning().getId())
                        .testErrorId(testStepToAdd.getTestError().getId())
                        .subTestId(testStepToAdd.getSubtest().getId())
                        .build();
        when(testStepDal.save(any(TestStepEntity.class))).thenReturn(addedTestStep);
        // Act
        CreateTestStepResponse response =
                testStepService.createTestResponse(request);
        // Assert 
        verify(testStepDal).save(any(TestStepEntity.class));
        assertNotNull(response);
        assertEquals(response.getId(), response.getId());
    }
    @Test
    public void GetAllTestStepsWithResultIdShouldReturnTestSteps_WhenTheyExist(){
        GetTestStepRequest request = new GetTestStepRequest(1);
        when(testStepDal.getAllTestStepsWithResultId(request.getId())).thenReturn(List.of(
                TestStepEntity.builder().id(1).stepNo(1).testError(testErrorEntity).testWarning(testWarningEntity).resultType(resultTypeEntity).build()
        ));
        Map<Integer, TestStep> expectedSteps = Map.of(
                1,
                TestStep.builder().id(1).stepNo(1).testError(testError).testWarning(testWarning).resultType(resultType).build()
        );
        GetTestStepsResponse response = testStepService.getAllTestStepsWithResultId(request);
        verify(testStepDal).getAllTestStepsWithResultId(request.getId());
        assertNotNull(response);
        assertEquals(expectedSteps, response.getTestSteps());
    }
    @Test
    public void GetAllTestStepsWithResultIdOfSubTestShouldReturnTestSteps_WhenTheyExist(){
        GetTestStepsWithResultIdOfSubTestRequest request =
                new GetTestStepsWithResultIdOfSubTestRequest(1, 1);
        Map<Integer, TestStep> expectedSteps = Map.of(
                1,
                TestStep.builder().id(1).stepNo(1).testError(testError).testWarning(testWarning).resultType(resultType).build()
        );
        when(testStepDal.getAllTestStepsWithResultIdOfSubTest(request.getResultId(), request.getSubtestId())).thenReturn(List.of(
                TestStepEntity.builder().id(1).stepNo(1).testError(testErrorEntity).testWarning(testWarningEntity).resultType(resultTypeEntity).build()
        ));
        GetTestStepsWithResultIdOfSubTestResponse response =
                testStepService.getAllTestStepsWithResultIdOfSubTest(request);
        verify(testStepDal).getAllTestStepsWithResultIdOfSubTest(request.getResultId(), request.getSubtestId());
        assertNotNull(response);
        assertEquals(expectedSteps, response.getTestSteps());
    }
    @Test
    public void GetTestStepsByErrorShouldReturnList_WhenErrorsExist(){
        GetTestStepsByErrorRequest request = new GetTestStepsByErrorRequest(1, 1);
        when(testStepDal.getTestStepsByError(request.getErrorId(),
                request.getSubTestId())).thenReturn(List.of(
                existentTestStep
        ));
        GetTestStepsByErrorResponse response = testStepService.getTestStepsByError(request);
        verify(testStepDal).getTestStepsByError(request.getErrorId(),
                request.getSubTestId());
        assertNotNull(response);
        assertEquals(Map.of(
                1, 
                TestStep.builder().id(1).stepNo(1).testError(testError).testWarning(testWarning).resultType(resultType).build()
        ), response.getTestSteps());
    }

    @Test
    void getCountTestStepsOfSubtestWithId() {
        GetTestStepsRequest request = new GetTestStepsRequest();
        request.setSubtestId(1);

        int expectedResponse = 6;
        when(testStepDal.getCountTestStepsOfSubtestWithId(1)).thenReturn(expectedResponse);

        int actualResponse = testStepDal.getCountTestStepsOfSubtestWithId(request.getSubtestId());

        assertEquals(expectedResponse, actualResponse);
        verify(testStepDal).getCountTestStepsOfSubtestWithId(1);
    }
    @Test
    public void testGetCountTestStepsOfSubtestWithId() {
        // Prepare test data
        int subtestId = 1;
        GetTestStepsRequest request = GetTestStepsRequest.builder().subtestId(subtestId).build();
        int expectedCount = 5;

        // Mock the behavior of testStepDal.getCountTestStepsOfSubtestWithId() to return the expected count
        when(testStepDal.getCountTestStepsOfSubtestWithId(request.getSubtestId())).thenReturn(expectedCount);

        // Execute the method under test
        Integer result = testStepService.getCountTestStepsOfSubtestWithId(request);

        // Verify the expected behavior and result
        assertEquals(expectedCount, result);

        // Verify the interactions with the mock object
        verify(testStepDal).getCountTestStepsOfSubtestWithId(request.getSubtestId());
    }
}