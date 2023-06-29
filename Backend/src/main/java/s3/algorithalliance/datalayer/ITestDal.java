package s3.algorithalliance.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import s3.algorithalliance.datalayer.entities.TestEntity;

import java.util.List;

public interface ITestDal extends JpaRepository<TestEntity, Integer> {
    @Query("select tr from TestEntity tr where tr.testSet.id = :testSetId")
    List<TestEntity> getAllTestResultsOfTestSetWithId(@Param("testSetId") Integer testSetId);
    @Query("select count(st) from SubtestEntity st " +
            "inner join st.testSteps ts where st.testResult.id = :testResultId " +
            "group by st having count(ts) = sum(case when ts.testError is null then 1 else 0 end)")
    Long getNumberOfSuccessfulSubtestsOfTestWithId(@Param("testResultId") Integer testResultId);
    @Query("select count(st) from SubtestEntity st where st.testResult.id=:testId")
    Long getNumberOfSubtestsInTestWithId(@Param("testId") Integer testId);
    @Query("SELECT subtest.id AS subtest_id, " +
            "CASE WHEN SUM(CASE WHEN ts.testError IS NULL THEN 1 ELSE 0 END) = COUNT(ts) THEN 1 ELSE 0 END AS is_successful " +
            "FROM SubtestEntity subtest " +
            "JOIN subtest.testSteps ts " +
            "WHERE subtest.testResult.id = :testId " +
            "GROUP BY subtest.id")
    List<Object[]> getResultsForSubtestsForTestWithId(@Param("testId") Integer testId);

    // FILTERS BELOW

    @Query("SELECT ts.id,ts.name,ts.testSet.id " +
            "FROM TestEntity ts " +
            "JOIN ts.subtests st " +
            "JOIN st.testSteps ts2 " +
            "WHERE ts.testSet.id =:testSetId  AND ts2.resultType.id=:resultId GROUP BY ts.id" )
    List<Object[]> getTestsWithResultOfTestSet(Integer testSetId, Integer resultId);
    @Query("SELECT ts.id,ts.name,ts.testSet.id " +
            "FROM TestEntity ts " +
            "JOIN ts.subtests st " +
            "JOIN st.testSteps ts2 " +
            "WHERE ts.testSet.id =:testSetId  AND ts2.testError.id=:errorId GROUP BY ts.id" )
    List<Object[]> getTestsWithErrorOfTestSet(Integer testSetId, Integer errorId);
    @Query("select COUNT(ts) from TestEntity ts where ts.testSet.id=:testSetId")

    Integer getCountTestsOfTestSetWithId(Integer testSetId);
}
