package s3.algorithalliance.domain.converters;


import s3.algorithalliance.datalayer.entities.ResultTypeEntity;
import s3.algorithalliance.datalayer.entities.TestErrorEntity;
import s3.algorithalliance.datalayer.entities.TestStepEntity;
import s3.algorithalliance.datalayer.entities.TestWarningEntity;
import s3.algorithalliance.domain.ResultType;
import s3.algorithalliance.domain.TestError;
import s3.algorithalliance.domain.TestStep;
import s3.algorithalliance.domain.TestWarning;

public final class TestStepConverter {
    private TestStepConverter(){}
    public static TestStep convert(TestStepEntity entity){
        
        TestError testError = null;
        if(entity.getTestError() != null) {
            testError = TestError
                    .builder()
                    .id(entity.getTestError().getId())
                    .errorCode(entity.getTestError().getErrorCode())
                    .build();
        }
        
        TestWarning testWarning = null;
        if(entity.getTestWarning() != null) {
            testWarning = TestWarning
                    .builder()
                    .id(entity.getTestWarning().getId())
                    .warningCode(entity.getTestWarning().getWarningCode())
                    .build();
        }
        return TestStep
                .builder()
                .id(entity.getId())
                .stepNo(entity.getStepNo())
                .result_details(entity.getResult_details())
                .resultType(ResultType.builder().id(entity.getResultType().getId()).build())
                .testError(
                        testError
                )
                .testWarning(
                        testWarning
                )
                .resultType(
                        ResultType
                                .builder()
                                .id(entity.getResultType().getId())
                                .resultName(entity.getResultType().getResultName())
                                .build()
                )
                .build();
    }
    public static TestStepEntity convert(TestStep step){
        TestErrorEntity testError = null;
        if(step.getTestError() != null) {
            testError = TestErrorEntity
                    .builder()
                    .id(step.getTestError().getId())
                    .errorCode(step.getTestError().getErrorCode())
                    .build();
        }

        TestWarningEntity testWarning = null;
        if(step.getTestWarning() != null) {
            testWarning = TestWarningEntity
                    .builder()
                    .id(step.getTestWarning().getId())
                    .warningCode(step.getTestWarning().getWarningCode())
                    .build();
        }
        return TestStepEntity
                .builder()
                .id(step.getId())
                .stepNo(step.getStepNo())
                .result_details(step.getResult_details())
                .resultType(ResultTypeEntity.builder().id(step.getResultType().getId()).build())
                .testError(
                        testError
                )
                .testWarning(
                        testWarning
                )
                .resultType(
                        ResultTypeEntity
                                .builder()
                                .id(step.getResultType().getId())
                                .resultName(step.getResultType().getResultName())
                                .build()
                )
                .build();
    }
}
