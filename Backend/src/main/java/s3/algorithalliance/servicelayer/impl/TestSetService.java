package s3.algorithalliance.servicelayer.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import s3.algorithalliance.datalayer.ITestSetDal;
import s3.algorithalliance.datalayer.entities.CommitEntity;
import s3.algorithalliance.datalayer.entities.TestSetEntity;
import s3.algorithalliance.domain.Commit;
import s3.algorithalliance.domain.Test;
import s3.algorithalliance.domain.TestSet;
import s3.algorithalliance.domain.converters.TestSetConverter;
import s3.algorithalliance.domain.reqresp.commit.GetCommitsRequest;
import s3.algorithalliance.domain.reqresp.commit.GetCommitsResponse;
import s3.algorithalliance.domain.reqresp.testres.GetTestResultsRequest;
import s3.algorithalliance.domain.reqresp.testset.*;
import s3.algorithalliance.servicelayer.ITestService;
import s3.algorithalliance.servicelayer.ITestSetService;
import s3.algorithalliance.servicelayer.exc.TestSetNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class TestSetService implements ITestSetService {
    private ITestSetDal testSetDal;
    private ITestService testService;

    @Override
    public GetTestSetsResponse getTestSetsOfCommitWithId(GetTestSetsRequest request) {
        Map<Integer, TestSet> testsOfCommit = new HashMap<>();
        testSetDal.getAllTestSetsOfCommitWithId(request.getCommitId()).stream().map(TestSetConverter::convert).forEach(
                ts -> testsOfCommit.put(ts.getId(), ts)
        );
        return new GetTestSetsResponse(testsOfCommit);
    }
    @Override
    public GetTestCountResponse getCountOfAllTestResultsOfTestSetWithId(GetTestCountRequest request){
        long count = testSetDal.getCountOfAllTestResultsOfTestSetWithId(request.getTestSetId());
        return new GetTestCountResponse(count);
    }

    @Override
    public GetTestCountResponse getCountOfSuccessfulTestsOfTestSetWithId(GetTestCountRequest request) {
        Long count = testSetDal.getNumberOfSuccessfulTestsInTestSetWithId(request.getTestSetId());
        return new GetTestCountResponse(count);
    }

    @Override
    public GetTestResultsInTestSetResponse getTestResultsInTestSetWithId(GetTestResultsInTestSetRequest request) {
        List<Object[]> results = testSetDal.getTestsOfTestStepAndTheirResults(request.getTestSetId());

        Map<Integer, Integer> response = new HashMap<>();
        for (Object[] result : results) {
            Integer testId = (Integer) result[0];
            Integer successFlag = (Integer) result[1];
            response.put(testId, successFlag);
        }

        return new GetTestResultsInTestSetResponse(response);
    }


    @Override
    public GetTestSetResponse getTestSet(GetTestSetRequest request) {
        TestSetEntity foundTestSet =
                testSetDal.findById(request.getTestSetId()).orElseThrow(TestSetNotFoundException::new);
        TestSet convertedTestSet = TestSetConverter.convert(foundTestSet);
        return new GetTestSetResponse(convertedTestSet);
    }

    @Override
    public CreateTestSetResponse createTestSet(CreateTestSetRequest request) {
        TestSetEntity testSetToAdd = TestSetEntity
                .builder()
                .name(request.getName())
                .commitEntity(CommitEntity.builder().id(request.getCommitId()).build())
                .testResults(new HashMap<>())
                .build();
        TestSetEntity createdEntity = testSetDal.save(testSetToAdd);
        return new CreateTestSetResponse(createdEntity.getId());
    }

    // FILTERS BELOW

    @Override
    public GetTestSetsResponse getTestSetsWithErrorOfCommit(GetTestSetsWithTestErrorsInCommitRequest request) {
        Map<Integer, TestSet> response = new HashMap<>();
        List<Object[]> results = testSetDal.getTestSetsWithErrorOfCommit(request.getCommitId(),
                request.getErrorId());
        for (Object[] result : results) {
            TestSetEntity testSetEntity = new TestSetEntity().builder()
                    .id((Integer) result[0])
                    .name(result[1].toString())
                    .commitEntity(CommitEntity.builder().id((int) result[2]).build())
                    .build();
            TestSet testSet = TestSetConverter.convert(testSetEntity);

            response.put(testSet.getId(), testSet);
        }
        return new GetTestSetsResponse(response);
    }

    @Override
    public GetTestSetsResponse getTestSetsWithResultsOfCommit(GetTestSetsWithTestResultsInCommitRequest request) {
        Map<Integer, TestSet> response = new HashMap<>();
        List<Object[]> results = testSetDal.getTestSetsWithResultsOfCommit(request.getCommitId(),
                request.getResultId());
        for (Object[] result : results) {
            TestSetEntity testSetEntity = new TestSetEntity().builder()
                    .id((Integer) result[0])
                    .name(result[1].toString())
                    .commitEntity(CommitEntity.builder().id((int) result[2]).build())
                    .build();
            TestSet testSet = TestSetConverter.convert(testSetEntity);

            response.put(testSet.getId(), testSet);
        }
        return new GetTestSetsResponse(response);
    }
    @Override
    public Integer getCountTestSetsOfCommitWithId(GetTestSetsRequest request) {
        Integer response = testSetDal.getCountTestSetsOfCommitWithId(request.getCommitId());

        return response;
    }

    // Alessandro Count
    @Override
    public Map<Integer, Integer> getTestsNumberOfTestSetInCommitWithId(Integer id) {
        Map<Integer, Integer> counts = new HashMap<>();

        GetTestSetsRequest request = GetTestSetsRequest.builder().commitId(id).build();
        GetTestSetsResponse testSetsOfCommit = this.getTestSetsOfCommitWithId(request);

        for (Map.Entry<Integer, TestSet> entryTestSet : testSetsOfCommit.getTestSets().entrySet())
        {
            GetTestResultsRequest testRequest = GetTestResultsRequest.builder().testSetId(entryTestSet.getValue().getId()).build();
            Integer testsNumber = testService.getTestResultsOfTestSetWithId(testRequest).getTestResults().size();
            counts.put(entryTestSet.getValue().getId(), testsNumber);
        }
        return counts;
    }

}
