package s3.algorithalliance.domain.converters;

import s3.algorithalliance.datalayer.entities.BranchEntity;
import s3.algorithalliance.datalayer.entities.UserEntity;
import s3.algorithalliance.domain.Branch;
import s3.algorithalliance.domain.User;

public final class BranchConverter {
    private BranchConverter(){}
    public static Branch convert(BranchEntity entity){
        User user = null;
        if(entity.getUserEntity() != null){
            user = User.builder().id(entity.getUserEntity().getId()).build();
        }
        return Branch
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .user(user)
                .build();
    }
    public static BranchEntity convert(Branch branch){
        UserEntity user = null;
        if(branch.getUser() != null){
            user = UserEntity.builder().id(branch.getUser().getId()).build();
        }
        return BranchEntity
                .builder()
                .id(branch.getId())
                .name(branch.getName())
                .userEntity(user)
                .build();
    }
}
