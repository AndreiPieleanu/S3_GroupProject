package s3.algorithalliance.domain.converters;

import s3.algorithalliance.datalayer.entities.TestEntity;
import s3.algorithalliance.datalayer.entities.TestSetEntity;
import s3.algorithalliance.domain.Test;
import s3.algorithalliance.domain.TestSet;

public class TestResultConverter {
    private TestResultConverter(){}
    public static Test convert(TestEntity entity){
        return Test
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .duration(entity.getDuration())
                .testSet(TestSet.builder().id(entity.getTestSet().getId()).build())
                .build();
    }
    public static TestEntity convert(Test test){
        return TestEntity
                .builder()
                .id(test.getId())
                .name(test.getName())
                .duration(test.getDuration())
                .testSet(TestSetEntity.builder().id(test.getTestSet().getId()).build())
                .build();
    }
}
