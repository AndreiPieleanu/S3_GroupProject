package s3.algorithalliance.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import s3.algorithalliance.datalayer.entities.TestSetEntity;

import java.util.List;
import java.util.Map;

public interface ITestSetDal extends JpaRepository<TestSetEntity, Integer> {
    @Query("select ts from TestSetEntity ts where ts.commitEntity.id = :commitId")
    List<TestSetEntity> getAllTestSetsOfCommitWithId(@Param("commitId") Integer commitId);
    @Query("select count(tr) from TestEntity tr " +
            "inner join tr.subtests st " +
            "where tr.testSet.id = :testSetId and " +
            "not exists (select 1 from TestStepEntity ts " +
            "where ts.subtest.id = st.id and ts.testError is not null)")
    Long getNumberOfSuccessfulTestsInTestSetWithId(@Param("testSetId") Integer testSetId);
    @Query("select count(tr) from TestEntity tr where tr.testSet.id = :testSetId")
    long getCountOfAllTestResultsOfTestSetWithId(@Param("testSetId") Integer testSetId);
    @Query("SELECT test.id, " +
            "CASE WHEN SUM(CASE WHEN ts.testError IS NULL THEN 1 ELSE 0 END) = COUNT(ts.id) THEN 1 ELSE 0 END " +
            "FROM TestEntity test " +
            "LEFT JOIN test.subtests subtest " +
            "LEFT JOIN subtest.testSteps ts " +
            "WHERE test.testSet.id = :testSetId " +
            "GROUP BY test.id")
    List<Object[]> getTestsOfTestStepAndTheirResults(@Param("testSetId") Integer testSetId);

    // FILTERS BELOW

    @Query("SELECT ts.id,ts.name,ts.commitEntity.id " +
            "FROM TestSetEntity ts " +
            "JOIN ts.testResults t " +
            "JOIN t.subtests st " +
            "JOIN st.testSteps ts2 " +
            "WHERE ts.commitEntity.id =:commitId  AND ts2.testError.id=:errorId GROUP BY ts.id" )
    List<Object[]> getTestSetsWithErrorOfCommit(Integer commitId, Integer errorId);

    @Query("SELECT ts.id,ts.name,ts.commitEntity.id " +
            "FROM TestSetEntity ts " +
            "JOIN ts.testResults t " +
            "JOIN t.subtests st " +
            "JOIN st.testSteps ts2 " +
            "WHERE ts.commitEntity.id =:commitId  AND ts2.resultType.id=:resultId GROUP BY ts.id" )
    List<Object[]> getTestSetsWithResultsOfCommit(Integer commitId, Integer resultId);
    @Query("select COUNT(ts) from TestSetEntity ts where ts.commitEntity.id=:commitId")

    Integer getCountTestSetsOfCommitWithId(Integer commitId);

}
