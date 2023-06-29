package s3.algorithalliance.servicelayer;

import s3.algorithalliance.domain.reqresp.commit.*;

import java.util.Map;

public interface ICommitService {
    GetCommitResponse getCommit(GetCommitRequest request);
    GetCommitsResponse getAllCommitsOfBranch(GetCommitsRequest request);
    CreateCommitResponse createCommit(CreateCommitRequest request);
    GetTestSetsResultsInCommitResponse getTestSetsResultsInCommitWithId(GetTestSetsResultsInCommitRequest request);
    GetResultsForTestSetsOfAllCommitsResponse getResultsForAllCommitsInBranch(GetResultsForTestSetsOfAllCommitsRequest request);
    GetResultsForAllCommitsOfBranchWithIdResponse getResultsForAllCommitsInBranchOrdered(GetResultsForTestSetsOfAllCommitsRequest request);
    Map<Integer, Integer> getResultsForAllCommitsInBranchForReact(GetResultsForTestSetsOfAllCommitsRequest request);

    // filters
    GetCommitsResponse getCommitWithErrorOfBranchWithId(GetCommitsWithTestErrorsInBranchRequest request);
    GetCommitsResponse getCommitsWithTestResultInBranchRequest(GetCommitsWithTestResultInBranchRequest request);
    GetCommitsResponse getCommitWithVersionOfBranchWithId(GetCommitsWithVersionInBranchRequest request);
    GetCommitsResponse getAllCommitsFromAllBranchesOfUserWithId(Integer userId);

    Integer getCountCommitsOfBranchWithId(GetCommitsRequest request);

    // Alessandro
    Map<Integer, Integer> getTestSetsNumberOfCommitsInBranchWithId(Integer id);
}
