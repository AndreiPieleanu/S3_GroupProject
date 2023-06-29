package s3.algorithalliance.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import s3.algorithalliance.datalayer.entities.CommitEntity;

import java.util.List;

public interface ICommitDal extends JpaRepository<CommitEntity, Integer> {
    @Query("select ce from CommitEntity ce where ce.originBranch.id=:branchId" +
            " order by ce.versionDate")
    List<CommitEntity> getAllEntitiesOfBranch(@Param(value = "branchId") Integer branchId);
    @Query("SELECT testSet.id, " +
            "CASE WHEN SUM(CASE WHEN ts.testError IS NULL THEN 1 ELSE 0 END) = COUNT(ts.id) THEN 1 ELSE 0 END " +
            "FROM TestSetEntity testSet " +
            "LEFT JOIN testSet.testResults tests " +
            "LEFT JOIN tests.subtests subtest " +
            "LEFT JOIN subtest.testSteps ts " +
            "WHERE testSet.commitEntity.id = :commitId " +
            "GROUP BY testSet.id")
    List<Object[]> getResultsForTestSetsOfCommitWithId(@Param("commitId") Integer commitId);
    @Query("SELECT commit, " +
            "CASE WHEN SUM(CASE WHEN ts.testError IS NULL THEN 1 ELSE 0 END) = COUNT(ts.id) THEN 1 ELSE 0 END " +
            "FROM CommitEntity commit " +
            "LEFT JOIN commit.testSets testSet " +
            "LEFT JOIN testSet.testResults test " +
            "LEFT JOIN test.subtests subtest " +
            "LEFT JOIN subtest.testSteps ts " +
            "WHERE commit.originBranch.id = :branchId " +
            "GROUP BY commit.id " +
            "order by commit.versionDate")
    List<Object[]> getResultsForAllCommitsInBranch(@Param("branchId") Integer branchId);

    // FILTERS BELOW

    @Query("SELECT c.id,c.name,c.originBranch.id,c.version,c.versionDate " +
            "FROM CommitEntity c " +
            "JOIN c.testSets ts " +
            "JOIN ts.testResults t " +
            "JOIN t.subtests st " +
            "JOIN st.testSteps ts2 " +
            "WHERE c.originBranch.id =:branchId AND ts2.testError.id =:errorId GROUP BY c.id" )
    List<Object[]> getCommitWithErrorOfBranchWithId(@Param("errorId") Integer errorId, @Param("branchId") Integer branchId);
    @Query("SELECT c.id,c.name,c.originBranch.id,c.version,c.versionDate " +
            "FROM CommitEntity c " +
            "JOIN c.testSets ts " +
            "JOIN ts.testResults t " +
            "JOIN t.subtests st " +
            "JOIN st.testSteps ts2 " +
            "WHERE c.originBranch.id =:branchId AND ts2.resultType.id =:resultId GROUP BY c.id" )
    List<Object[]> getCommitsWithTestResultInBranchRequest(@Param("resultId") Integer resultId, @Param("branchId") Integer branchId);
    @Query("SELECT c.id,c.name,c.originBranch.id,c.version,c.versionDate " +
            "FROM CommitEntity c " +
            "JOIN c.testSets ts " +
            "JOIN ts.testResults t " +
            "JOIN t.subtests st " +
            "JOIN st.testSteps ts2 " +
            "WHERE c.originBranch.id =:branchId AND c.version LIKE CONCAT('%',:version,'%')  GROUP BY c.id" )
    List<Object[]> getCommitWithVersionOfBranchWithId(@Param("branchId") Integer branchId,@Param("version") String version);
    @Query("select c " +
            "from CommitEntity c " +
            "join c.originBranch b " +
            "where b.userEntity.id=:userId or b.userEntity.id=NULL")
    List<CommitEntity> getAllCommitsPostedByUserWithId(@Param("userId") Integer userId);
    @Query("select COUNT(ts) from CommitEntity ts where ts.originBranch.id=:branchId")
    Integer getCountCommitsOfBranchWithId(Integer branchId);
}
