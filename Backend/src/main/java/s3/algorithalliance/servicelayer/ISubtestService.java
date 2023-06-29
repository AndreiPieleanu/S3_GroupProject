package s3.algorithalliance.servicelayer;

import s3.algorithalliance.domain.reqresp.subtest.*;

import java.util.Map;

public interface ISubtestService {
    GetSubtestResponse getSubtest(GetSubtestRequest request);
    CreateSubtestResponse createSubtest(CreateSubtestRequest request);
    GetSubtestsResponse getSubtestsOfTestWithId(GetSubtestsRequest request);
    GetTestStepsCountResponse getSuccessfulTestStepsOfSubtestWithId(GetTestStepsCountRequest request);
    GetTestStepsCountResponse getTestStepsCountOfSubtestWithId(GetTestStepsCountRequest request);

    // FILTERS BELOW

    GetSubtestsResponse getSubTestsWithResultOfTest(GetSubTestWithTestResultsOfTestRequest request);
    GetSubtestsResponse getSubTestsWithErrorOfTest(GetSUbTestWithTestErrorsOfTestRequest request);

    Integer getCountSubTestsOfTestWithId(GetSubtestsRequest request);

    // Alessandro Count
    Map<Integer, Integer> getTestStepsNumberOfSubestInTestWithId(Integer id);
}
