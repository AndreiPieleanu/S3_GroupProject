package s3.algorithalliance.domain.converters;

import s3.algorithalliance.datalayer.entities.BranchEntity;
import s3.algorithalliance.datalayer.entities.CommitEntity;
import s3.algorithalliance.domain.Branch;
import s3.algorithalliance.domain.Commit;

public final class CommitConverter {
    private CommitConverter(){}
    public static Commit convert(CommitEntity entity){
        return Commit
                .builder()
                .id(entity.getId())
                .version(entity.getVersion())
                .versionDate(entity.getVersionDate())
                .name(entity.getName())
                .originBranch(Branch.builder().id(entity.getOriginBranch().getId()).build())
                .build();
    }
    public static CommitEntity convert(Commit commit){
        return CommitEntity
                .builder()
                .id(commit.getId())
                .version(commit.getVersion())
                .versionDate(commit.getVersionDate())
                .name(commit.getName())
                .originBranch(BranchEntity.builder().id(commit.getOriginBranch().getId()).build())
                .build();
    }
}
