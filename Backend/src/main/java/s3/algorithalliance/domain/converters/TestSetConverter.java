package s3.algorithalliance.domain.converters;

import s3.algorithalliance.datalayer.entities.CommitEntity;
import s3.algorithalliance.datalayer.entities.TestSetEntity;
import s3.algorithalliance.domain.Branch;
import s3.algorithalliance.domain.Commit;
import s3.algorithalliance.domain.TestSet;

public final class TestSetConverter {
    private TestSetConverter(){}
    public static TestSet convert(TestSetEntity entity){
        return TestSet
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .commit(Commit.builder().id(entity.getCommitEntity().getId()).build())
                .build();
    }
    public static TestSetEntity convert(TestSet testSet){
        return TestSetEntity
                .builder()
                .id(testSet.getId())
                .name(testSet.getName())
                .commitEntity(CommitEntity.builder().id(testSet.getCommit().getId()).build())
                .build();
    }
}
