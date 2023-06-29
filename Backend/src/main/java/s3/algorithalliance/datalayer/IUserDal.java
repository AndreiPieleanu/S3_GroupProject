package s3.algorithalliance.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import s3.algorithalliance.datalayer.entities.UserEntity;

import java.util.Optional;

public interface IUserDal extends JpaRepository<UserEntity, Integer> {
    @Query("select user from UserEntity user where user.email=:email")
    Optional<UserEntity> findByEmail(
            @Param("email") String email);
    @Modifying
    @Query("update UserEntity us set us.email=:#{#userEntity.email}, " +
            "us.username=:#{#userEntity.username}, " +
            "us.password=:#{#userEntity.password} where us.id=:#{#userEntity.id}")
    Integer updateUser(UserEntity userEntity);
}
