package s3.algorithalliance.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import s3.algorithalliance.datalayer.entities.BranchEntity;

import java.util.List;

public interface IBranchDal extends JpaRepository<BranchEntity, Integer> {
    @Query("select be from BranchEntity be where be.userEntity.id=NULL")
    List<BranchEntity> findAllPublicBranches();
    @Query("select be from BranchEntity be where be.userEntity.id=NULL or be.userEntity.id=:userId")
    List<BranchEntity> findAllBranchesForUserWithId(@Param("userId")Integer userId);
}
