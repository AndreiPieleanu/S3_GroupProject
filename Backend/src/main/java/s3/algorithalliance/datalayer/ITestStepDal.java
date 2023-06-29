package s3.algorithalliance.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import s3.algorithalliance.datalayer.entities.TestStepEntity;

import java.util.List;

public interface ITestStepDal extends JpaRepository<TestStepEntity, Integer> {
    @Query("select ts from TestStepEntity ts where ts.subtest.id=:subtestId")
    List<TestStepEntity> getAllTestStepsOfSubtestWithId(@Param("subtestId") Integer subtestId);
    @Query("SELECT ts FROM TestStepEntity ts WHERE ts.resultType.id = :resultTypeId")
    List<TestStepEntity> getAllTestStepsWithResultId(@Param("resultTypeId") Integer resultTypeId);
    @Query("SELECT ts FROM TestStepEntity ts WHERE ts.resultType.id = :resultTypeId AND ts.subtest.id = :subTestId")
    List<TestStepEntity> getAllTestStepsWithResultIdOfSubTest(@Param("resultTypeId") Integer resultTypeId,@Param("subTestId") Integer subTestId);
    @Query("select ts from TestStepEntity ts where ts.testError.id=:errorId AND ts.subtest.id = :subTestId")
    List<TestStepEntity> getTestStepsByError(@Param("errorId") Integer errorId,@Param("subTestId") Integer subTestId);
    @Query("select COUNT(ts) from TestStepEntity ts where ts.subtest.id=:subtestId")
    Integer getCountTestStepsOfSubtestWithId(@Param("subtestId") Integer subtestId);
}