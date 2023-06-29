package s3.algorithalliance.servicelayer.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import s3.algorithalliance.datalayer.ITestStepDal;
import s3.algorithalliance.datalayer.entities.*;
import s3.algorithalliance.domain.TestStep;
import s3.algorithalliance.domain.converters.TestStepConverter;
import s3.algorithalliance.domain.reqresp.teststep.*;
import s3.algorithalliance.servicelayer.ITestStepService;
import s3.algorithalliance.servicelayer.exc.TestStepNotFoundException;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class TestStepService implements ITestStepService {
    private ITestStepDal testStepDal;

    @Override
    public GetTestStepsResponse getAllTestStepsOfSubtestWithId(GetTestStepsRequest request) {
        Map<Integer, TestStep> testSteps = new HashMap<>();
        testStepDal
                .getAllTestStepsOfSubtestWithId(request.getSubtestId())
                .stream()
                .map(TestStepConverter::convert)
                .forEach(ts -> testSteps.put(ts.getId(), ts));
        return new GetTestStepsResponse(testSteps);
    }

    @Override
    public GetTestStepsResponse getAllTestStepsWithResultId(GetTestStepRequest request) {
        Map<Integer, TestStep> testSteps = new HashMap<>();
        testStepDal
                .getAllTestStepsWithResultId(request.getId())
                .stream()
                .map(TestStepConverter::convert)
                .forEach(ts -> testSteps.put(ts.getId(), ts));
        return new GetTestStepsResponse(testSteps);
    }

    @Override
    public GetTestStepsWithResultIdOfSubTestResponse getAllTestStepsWithResultIdOfSubTest(GetTestStepsWithResultIdOfSubTestRequest request) {
        Map<Integer, TestStep> testSteps = new HashMap<>();
        testStepDal
                .getAllTestStepsWithResultIdOfSubTest(request.getResultId(), request.getSubtestId())
                .stream()
                .map(TestStepConverter::convert)
                .forEach(ts -> testSteps.put(ts.getId(), ts));
        return new GetTestStepsWithResultIdOfSubTestResponse(testSteps);
    }

    @Override
    public GetTestStepResponse getTestStep(GetTestStepRequest request) {
        TestStepEntity foundEntity =
                testStepDal.findById(request.getId()).orElseThrow(TestStepNotFoundException::new);
        TestStep convertedTestStep = TestStepConverter.convert(foundEntity);
        return new GetTestStepResponse(convertedTestStep);
    }

    @Override
    public CreateTestStepResponse createTestResponse(CreateTestStepRequest request) {
        TestStepEntity entityToCreate = TestStepEntity
                .builder()
                .stepNo(request.getStepNo())
                .result_details(request.getResultDetails())
                .testError(TestErrorEntity.builder().id(request.getTestErrorId()).build())
                .testWarning(TestWarningEntity.builder().id(request.getTestWarningId()).build())
                .resultType(ResultTypeEntity.builder().id(request.getResultTypeId()).build())
                .subtest(SubtestEntity.builder().id(request.getSubTestId()).build())
                .build();
        TestStepEntity createdEntity = testStepDal.save(entityToCreate);
        return new CreateTestStepResponse(createdEntity.getId());
    }

    @Override
    public GetTestStepsByErrorResponse getTestStepsByError(GetTestStepsByErrorRequest request) {
        Map<Integer, TestStep> testSteps = new HashMap<>();
        testStepDal
                .getTestStepsByError(request.getErrorId(),request.getSubTestId())
                .stream()
                .map(TestStepConverter::convert)
                .forEach(ts -> testSteps.put(ts.getTestError().getId(), ts));
        return new GetTestStepsByErrorResponse(testSteps);
    }
    @Override
    public Integer getCountTestStepsOfSubtestWithId(GetTestStepsRequest request){
        return testStepDal.getCountTestStepsOfSubtestWithId(request.getSubtestId());

    }
}
