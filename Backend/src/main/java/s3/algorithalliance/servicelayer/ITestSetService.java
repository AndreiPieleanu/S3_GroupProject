package s3.algorithalliance.servicelayer;

import s3.algorithalliance.domain.reqresp.testset.*;

import java.util.Map;

public interface ITestSetService {
    GetTestSetsResponse getTestSetsOfCommitWithId(GetTestSetsRequest request);
    GetTestSetResponse getTestSet(GetTestSetRequest request);
    CreateTestSetResponse createTestSet(CreateTestSetRequest request);
    GetTestCountResponse getCountOfAllTestResultsOfTestSetWithId(GetTestCountRequest request);
    GetTestCountResponse getCountOfSuccessfulTestsOfTestSetWithId(GetTestCountRequest request);
    GetTestResultsInTestSetResponse getTestResultsInTestSetWithId(GetTestResultsInTestSetRequest request);

    //Filters
    GetTestSetsResponse getTestSetsWithResultsOfCommit(GetTestSetsWithTestResultsInCommitRequest request);

    GetTestSetsResponse getTestSetsWithErrorOfCommit(GetTestSetsWithTestErrorsInCommitRequest request);

    Integer getCountTestSetsOfCommitWithId(GetTestSetsRequest request);

    // Alessandro Count
    Map<Integer, Integer> getTestsNumberOfTestSetInCommitWithId(Integer id);
}
