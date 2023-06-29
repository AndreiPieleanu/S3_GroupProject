package s3.algorithalliance.domain.converters;

import s3.algorithalliance.datalayer.entities.UserEntity;
import s3.algorithalliance.domain.User;

public final class UserConverter {
    private UserConverter(){}
    public static User convert(UserEntity entity){
        return User
                .builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .build();
    }
    public static UserEntity convert(User user){
        return UserEntity
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
