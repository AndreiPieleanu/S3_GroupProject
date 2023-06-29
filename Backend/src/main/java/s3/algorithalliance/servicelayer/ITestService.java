package s3.algorithalliance.servicelayer;

import s3.algorithalliance.domain.reqresp.testres.*;

import java.util.Map;

public interface ITestService {
    GetTestResultsResponse getTestResultsOfTestSetWithId(GetTestResultsRequest request);
    GetTestResultResponse getTestResult(GetTestResultRequest request);
    CreateTestResultResponse createTestResult(CreateTestResultRequest request);
    GetSubtestsCountResponse getSubtestsOfTestWithId(GetSubtestsCountRequest request);
    GetSubtestsCountResponse getSuccessfulSubtestsOfTestWithId(GetSubtestsCountRequest request);
    GetSubtestsResultsInTestResponse getSubtestsResultsInTestWithId(GetSubtestsResultsInTestRequest request);

    // FILTERS BELOW
    GetTestResultsResponse getTestsWithResultOfTestSet(GetTestWithTestResultsOfTestSetRequest request);
    GetTestResultsResponse getTestsWithErrorOfTestSet(GetTestWithTestErrorsOfTestSetRequest request);

    Integer getCountTestsOfTestSetWithId(GetTestResultsRequest request);

    // Alessandro Count
    Map<Integer, Integer> getSubtestsNumberOfTestInTestSetWithId(Integer id);
}
