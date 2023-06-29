package s3.algorithalliance.domain.converters;


import s3.algorithalliance.datalayer.entities.SubtestEntity;
import s3.algorithalliance.datalayer.entities.TestEntity;
import s3.algorithalliance.domain.SubTest;
import s3.algorithalliance.domain.Test;
import s3.algorithalliance.domain.TestStep;

public final class SubTestConverter {
    private SubTestConverter(){}
    public static SubTest convert(SubtestEntity entity){
        return SubTest
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .test(Test.builder().id(entity.getTestResult().getId()).build())
                .build();
    }
    public static SubtestEntity convert(SubTest test){
        return SubtestEntity
                .builder()
                .id(test.getId())
                .name(test.getName())
                .testResult(TestEntity.builder().id(test.getTest().getId()).build())
                .build();
    }
}
