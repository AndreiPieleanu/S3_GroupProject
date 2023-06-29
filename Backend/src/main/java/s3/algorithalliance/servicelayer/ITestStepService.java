package s3.algorithalliance.servicelayer;

import s3.algorithalliance.domain.reqresp.teststep.*;

public interface ITestStepService {
    GetTestStepsResponse getAllTestStepsOfSubtestWithId(GetTestStepsRequest request);
    GetTestStepsResponse getAllTestStepsWithResultId(GetTestStepRequest request);

    GetTestStepsWithResultIdOfSubTestResponse getAllTestStepsWithResultIdOfSubTest(GetTestStepsWithResultIdOfSubTestRequest request);

    GetTestStepResponse getTestStep(GetTestStepRequest request);
    GetTestStepsByErrorResponse getTestStepsByError(GetTestStepsByErrorRequest request);
    CreateTestStepResponse createTestResponse(CreateTestStepRequest request);

    Integer getCountTestStepsOfSubtestWithId(GetTestStepsRequest request);
}
