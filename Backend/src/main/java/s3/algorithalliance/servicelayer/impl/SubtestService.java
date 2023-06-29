package s3.algorithalliance.servicelayer.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import s3.algorithalliance.datalayer.ISubTestDal;
import s3.algorithalliance.datalayer.entities.SubtestEntity;
import s3.algorithalliance.datalayer.entities.TestEntity;
import s3.algorithalliance.domain.SubTest;
import s3.algorithalliance.domain.Test;
import s3.algorithalliance.domain.converters.SubTestConverter;
import s3.algorithalliance.domain.reqresp.subtest.*;
import s3.algorithalliance.domain.reqresp.testres.GetTestResultsRequest;
import s3.algorithalliance.domain.reqresp.testres.GetTestResultsResponse;
import s3.algorithalliance.domain.reqresp.teststep.GetTestStepsRequest;
import s3.algorithalliance.servicelayer.ISubtestService;
import s3.algorithalliance.servicelayer.ITestStepService;
import s3.algorithalliance.servicelayer.exc.SubtestNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SubtestService implements ISubtestService {
    private ISubTestDal subTestDal;
    private ITestStepService testStepService;

    @Override
    public GetSubtestsResponse getSubtestsOfTestWithId(GetSubtestsRequest request) {
        Map<Integer, SubTest> foundSubtests = new HashMap<>();
        subTestDal.getAllSubtestsOfTestResultWithId(request.getTestResultId()).stream().map(SubTestConverter::convert).forEach(st -> foundSubtests.put(st.getId(), st));
        return new GetSubtestsResponse(foundSubtests);
    }

    @Override
    public GetTestStepsCountResponse getSuccessfulTestStepsOfSubtestWithId(GetTestStepsCountRequest request) {
        Long count = subTestDal.getNumberOfSuccessfulTestStepsForSubtestWithId(request.getSubtestId());
        return new GetTestStepsCountResponse(count);
    }

    @Override
    public GetTestStepsCountResponse getTestStepsCountOfSubtestWithId(GetTestStepsCountRequest request) {
        Long count = subTestDal.getNumberOfTestStepsFromSubtestWithId(request.getSubtestId());
        return new GetTestStepsCountResponse(count);
    }

    @Override
    public GetSubtestResponse getSubtest(GetSubtestRequest request) {
        SubtestEntity foundSubtestEntity =
                subTestDal.findById(request.getSubtestId()).orElseThrow(SubtestNotFoundException::new);
        SubTest convertedSubtest = SubTestConverter.convert(foundSubtestEntity);
        return new GetSubtestResponse(convertedSubtest);
    }

    @Override
    public CreateSubtestResponse createSubtest(CreateSubtestRequest request) {
        SubtestEntity entityToCreate = SubtestEntity
                .builder()
                .name(request.getName())
                .testResult(TestEntity.builder().id(request.getTestId()).build())
                .testSteps(new HashMap<>())
                .build();
        SubtestEntity createdEntity = subTestDal.save(entityToCreate);
        return new CreateSubtestResponse(createdEntity.getId());
    }

    // FILTERS BELOW

    @Override
    public GetSubtestsResponse getSubTestsWithResultOfTest(GetSubTestWithTestResultsOfTestRequest request) {
        Map<Integer, SubTest> response = new HashMap<>();
        List<Object[]> results = subTestDal.getSubTestsWithResultOfTest(request.getTestId(),
                request.getResultId());
        for (Object[] result : results) {
            SubtestEntity testSetEntity = new SubtestEntity().builder()
                    .id((Integer) result[0])
                    .name(result[1].toString())
                    .testResult(TestEntity.builder().id((int) result[2]).build())
                    .build();
            SubTest test = SubTestConverter.convert(testSetEntity);

            response.put(test.getId(), test);
        }
        return new GetSubtestsResponse(response);
    }

    @Override
    public GetSubtestsResponse getSubTestsWithErrorOfTest(GetSUbTestWithTestErrorsOfTestRequest request) {
        Map<Integer, SubTest> response = new HashMap<>();
        List<Object[]> results = subTestDal.getSubTestsWithErrorOfTest(request.getTestId(),
                request.getErrorId());
        for (Object[] result : results) {
            SubtestEntity testSetEntity = new SubtestEntity().builder()
                    .id((Integer) result[0])
                    .name(result[1].toString())
                    .testResult(TestEntity.builder().id((int) result[2]).build())
                    .build();
            SubTest test = SubTestConverter.convert(testSetEntity);

            response.put(test.getId(), test);
        }
        return new GetSubtestsResponse(response);
    }
    @Override
    public Integer getCountSubTestsOfTestWithId(GetSubtestsRequest request){
        return subTestDal.getCountSubTestsOfTestWithId(request.getTestResultId());

    }

    @Override
    public Map<Integer, Integer> getTestStepsNumberOfSubestInTestWithId(Integer id) {
        Map<Integer, Integer> counts = new HashMap<>();

        GetSubtestsRequest request = GetSubtestsRequest.builder().testResultId(id).build();
        GetSubtestsResponse subtestsOfTest = this.getSubtestsOfTestWithId(request);

        for (Map.Entry<Integer, SubTest> entrySubtest : subtestsOfTest.getSubtests().entrySet())
        {
            GetTestStepsRequest subtestRequest = GetTestStepsRequest.builder().subtestId(entrySubtest.getValue().getId()).build();
            Integer testsStepsNumber = testStepService.getAllTestStepsOfSubtestWithId(subtestRequest).getTestSteps().size();
            counts.put(entrySubtest.getValue().getId(), testsStepsNumber);
        }

        return counts;
    }
}
