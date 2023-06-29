package s3.algorithalliance.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import s3.algorithalliance.datalayer.entities.SubtestEntity;

import java.util.List;

public interface ISubTestDal extends JpaRepository<SubtestEntity, Integer> {
    @Query("select st from SubtestEntity st where st.testResult.id=:testResultId")
    List<SubtestEntity> getAllSubtestsOfTestResultWithId(@Param("testResultId") Integer testResultId);
    @Query("SELECT COUNT(ts) FROM TestStepEntity ts WHERE ts.subtest.id = :subtestId AND ts.testError IS NULL")
    Long getNumberOfSuccessfulTestStepsForSubtestWithId(@Param("subtestId") Integer subtestId);
    @Query("SELECT COUNT(ts) FROM TestStepEntity ts WHERE ts.subtest.id = :subtestId")
    Long getNumberOfTestStepsFromSubtestWithId(@Param("subtestId") Integer subtestId);

    // FILTERS BELOW

    @Query("SELECT ts.id,ts.name,ts.testResult.id " +
            "FROM SubtestEntity ts " +
            "JOIN ts.testSteps st " +
            "WHERE ts.testResult.id =:testId  AND st.resultType.id=:resultId GROUP BY ts.id" )
    List<Object[]> getSubTestsWithResultOfTest(Integer testId, Integer resultId);
    @Query("SELECT ts.id,ts.name,ts.testResult.id " +
            "FROM SubtestEntity ts " +
            "JOIN ts.testSteps st " +
            "WHERE ts.testResult.id =:testId  AND st.testError.id=:errorId GROUP BY ts.id" )
    List<Object[]> getSubTestsWithErrorOfTest(Integer testId, Integer errorId);
    @Query("select COUNT(ts) from SubtestEntity ts where ts.testResult.id=:testId")

    Integer getCountSubTestsOfTestWithId(Integer testId);
}
