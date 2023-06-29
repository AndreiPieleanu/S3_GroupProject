package s3.algorithalliance.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import s3.algorithalliance.datalayer.entities.RoleEnum;
import s3.algorithalliance.datalayer.entities.UserRoleEntity;

import java.util.Optional;

public interface IUserRoleDal extends JpaRepository<UserRoleEntity, Integer> {
    Optional<UserRoleEntity> findByRole(RoleEnum role);
}
