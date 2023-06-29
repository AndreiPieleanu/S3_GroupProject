package s3.algorithalliance.servicelayer;

import s3.algorithalliance.domain.CommitError;
import s3.algorithalliance.domain.PieChartStats;
import s3.algorithalliance.domain.reqresp.branches.*;

import java.util.List;
import java.util.Map;

public interface IBranchService {
    GetPublicBranchesResponse getAllPublicBranches();
    GetUserBranchesResponse getBranchesOfUser(GetBranchRequest request);
    GetBranchResponse getBranch(GetBranchRequest request);
    CreateBranchResponse createBranch(CreateBranchRequest request);
    List<CommitError> getCommitErrors();
    List<CommitError> getCommitErrorsForAuthedUser(Integer userId);
    Map getBranchResults();
    Map getBranchResultsForAuthed(Integer userId);
    Map<Integer, String> getLastVersionOfBranches();
    Map<Integer, String> getLastVersionOfBranchesAuthed(Integer userId);
    PieChartStats getStats();
    PieChartStats getStatsAuthed(Integer userId);
}
