package s3.algorithalliance.servicelayer.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import s3.algorithalliance.datalayer.ITestDal;
import s3.algorithalliance.datalayer.entities.TestEntity;
import s3.algorithalliance.datalayer.entities.TestSetEntity;
import s3.algorithalliance.domain.Test;
import s3.algorithalliance.domain.TestSet;
import s3.algorithalliance.domain.converters.TestResultConverter;
import s3.algorithalliance.domain.reqresp.subtest.GetSubtestRequest;
import s3.algorithalliance.domain.reqresp.subtest.GetSubtestsRequest;
import s3.algorithalliance.domain.reqresp.testres.*;
import s3.algorithalliance.domain.reqresp.testset.GetTestSetsRequest;
import s3.algorithalliance.domain.reqresp.testset.GetTestSetsResponse;
import s3.algorithalliance.servicelayer.ISubtestService;
import s3.algorithalliance.servicelayer.ITestService;
import s3.algorithalliance.servicelayer.exc.TestResultNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class TestService implements ITestService {
    private ITestDal testDal;
    private ISubtestService subtestService;

    @Override
    public GetTestResultsResponse getTestResultsOfTestSetWithId(GetTestResultsRequest request) {
        Map<Integer, Test> testResults = new HashMap<>();
        testDal.getAllTestResultsOfTestSetWithId(request.getTestSetId()).stream().map(TestResultConverter::convert).forEach(tr -> testResults.put(tr.getId(), tr));
        return new GetTestResultsResponse(testResults);
    }

    @Override
    public GetTestResultResponse getTestResult(GetTestResultRequest request) {
        TestEntity foundEntity =
                testDal.findById(request.getTestId()).orElseThrow(TestResultNotFoundException::new);
        Test convertedResult = TestResultConverter.convert(foundEntity);
        return new GetTestResultResponse(convertedResult);
    }

    @Override
    public CreateTestResultResponse createTestResult(CreateTestResultRequest request) {
        TestEntity entityToAdd = TestEntity
                .builder()
                .name(request.getName())
                .testSet(TestSetEntity.builder().id(request.getTestSetId()).build())
                .subtests(new HashMap<>())
                .duration(request.getDuration())
                .build();
        TestEntity createdEntity = testDal.save(entityToAdd);
        return new CreateTestResultResponse(createdEntity.getId());
    }

    @Override
    public GetSubtestsCountResponse getSubtestsOfTestWithId(GetSubtestsCountRequest request) {
        Long count = testDal.getNumberOfSubtestsInTestWithId(request.getTestId());
        return new GetSubtestsCountResponse(count);
    }

    @Override
    public GetSubtestsCountResponse getSuccessfulSubtestsOfTestWithId(GetSubtestsCountRequest request) {
        Long count = testDal.getNumberOfSuccessfulSubtestsOfTestWithId(request.getTestId());
        return new GetSubtestsCountResponse(count);
    }

    @Override
    public GetSubtestsResultsInTestResponse getSubtestsResultsInTestWithId(GetSubtestsResultsInTestRequest request) {
        List<Object[]> results = testDal.getResultsForSubtestsForTestWithId(request.getTestId());

        Map<Integer, Integer> response = new HashMap<>();
        for (Object[] result : results) {
            Integer testId = (Integer) result[0];
            Integer successFlag = (Integer) result[1];
            response.put(testId, successFlag);
        }

        return new GetSubtestsResultsInTestResponse(response);
    }

    // FILTERS BELOW

    @Override
    public GetTestResultsResponse getTestsWithResultOfTestSet(GetTestWithTestResultsOfTestSetRequest request) {
        Map<Integer, Test> response = new HashMap<>();
        List<Object[]> results = testDal.getTestsWithResultOfTestSet(request.getTestSetId(),
                request.getResultId());
        for (Object[] result : results) {
            TestEntity testSetEntity = new TestEntity().builder()
                    .id((Integer) result[0])
                    .name(result[1].toString())
                    .testSet(TestSetEntity.builder().id((int) result[2]).build())
                    .build();
            Test test = TestResultConverter.convert(testSetEntity);

            response.put(test.getId(), test);
        }
        return new GetTestResultsResponse(response);
    }

    @Override
    public GetTestResultsResponse getTestsWithErrorOfTestSet(GetTestWithTestErrorsOfTestSetRequest request) {
        Map<Integer, Test> response = new HashMap<>();
        List<Object[]> results = testDal.getTestsWithErrorOfTestSet(request.getTestSetId(),
                request.getErrorId());
        for (Object[] result : results) {
            TestEntity testSetEntity = new TestEntity().builder()
                    .id((Integer) result[0])
                    .name(result[1].toString())
                    .testSet(TestSetEntity.builder().id((int) result[2]).build())
                    .build();
            Test test = TestResultConverter.convert(testSetEntity);
            response.put(test.getId(), test);
        }
        return new GetTestResultsResponse(response);
    }

    @Override
    public Integer getCountTestsOfTestSetWithId(GetTestResultsRequest request) {
        Integer response = testDal.getCountTestsOfTestSetWithId(request.getTestSetId());

        return response;
    }

    @Override
    public Map<Integer, Integer> getSubtestsNumberOfTestInTestSetWithId(Integer id) {
        Map<Integer, Integer> counts = new HashMap<>();

        GetTestResultsRequest request = GetTestResultsRequest.builder().testSetId(id).build();
        GetTestResultsResponse testsOfTestSet = this.getTestResultsOfTestSetWithId(request);

        for (Map.Entry<Integer, Test> entryTest : testsOfTestSet.getTestResults().entrySet())
        {
            GetSubtestsRequest subtestRequest = GetSubtestsRequest.builder().testResultId(entryTest.getValue().getId()).build();
            Integer subtestsNumber = subtestService.getSubtestsOfTestWithId(subtestRequest).getSubtests().size();
            counts.put(entryTest.getValue().getId(), subtestsNumber);
        }
        return counts;
    }

}
