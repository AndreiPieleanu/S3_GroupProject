package s3.algorithalliance.domain.converters;

import s3.algorithalliance.datalayer.entities.TestErrorEntity;
import s3.algorithalliance.domain.TestError;

public final class ErrorConverter {
    private  ErrorConverter(){}
    public static TestError convert(TestErrorEntity entity){
        return TestError.builder()
                .id(entity.getId())
                .errorCode(entity.getErrorCode())
                .build();
    }

    public static TestErrorEntity convert(TestError error){
        return  TestErrorEntity.builder()
                .id(error.getId())
                .errorCode(error.getErrorCode())
                .build();
    }

}
