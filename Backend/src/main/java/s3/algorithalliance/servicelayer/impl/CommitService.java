package s3.algorithalliance.servicelayer.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import s3.algorithalliance.datalayer.ICommitDal;
import s3.algorithalliance.datalayer.entities.BranchEntity;
import s3.algorithalliance.datalayer.entities.CommitEntity;
import s3.algorithalliance.domain.Branch;
import s3.algorithalliance.domain.Commit;
import s3.algorithalliance.domain.converters.CommitConverter;
import s3.algorithalliance.domain.reqresp.commit.*;
import s3.algorithalliance.domain.reqresp.testset.GetTestSetsRequest;
import s3.algorithalliance.servicelayer.ICommitService;
import s3.algorithalliance.servicelayer.ITestSetService;
import s3.algorithalliance.servicelayer.exc.CommitNotFoundException;

import java.sql.Timestamp;
import java.util.*;

@Service
@AllArgsConstructor
public class CommitService implements ICommitService {
    private ICommitDal commitDal;
    private ITestSetService testSetService;

    @Override
    public GetCommitResponse getCommit(GetCommitRequest request) {
        CommitEntity foundCommit =
                commitDal.findById(request.getCommitId()).orElseThrow(CommitNotFoundException::new);
        Commit convertedCommit = CommitConverter.convert(foundCommit);
        return new GetCommitResponse(convertedCommit);
    }

    @Override
    public GetCommitsResponse getAllCommitsOfBranch(GetCommitsRequest request) {
        Map<Integer, Commit> commitsOfBranch = new HashMap<>();
        commitDal.getAllEntitiesOfBranch(request.getBranchId()).forEach(c -> commitsOfBranch.put(c.getId(), CommitConverter.convert(c)));
        return new GetCommitsResponse(commitsOfBranch);
    }

    @Override
    public CreateCommitResponse createCommit(CreateCommitRequest request) {
        CommitEntity commitToAdd = CommitEntity
                .builder()
                .name(request.getName())
                .originBranch(BranchEntity.builder().id(request.getBranchId()).build())
                .version(request.getVersion())
                .testSets(new HashMap<>())
                .build();
        CommitEntity addedCommit = commitDal.save(commitToAdd);
        return new CreateCommitResponse(addedCommit.getId());
    }

    @Override
    public GetTestSetsResultsInCommitResponse getTestSetsResultsInCommitWithId(GetTestSetsResultsInCommitRequest request) {
        List<Object[]> results = commitDal.getResultsForTestSetsOfCommitWithId(request.getCommitId());

        Map<Integer, Integer> response = new HashMap<>();
        for (Object[] result : results) {
            Integer testId = (Integer) result[0];
            Integer successFlag = (Integer) result[1];
            response.put(testId, successFlag);
        }

        return new GetTestSetsResultsInCommitResponse(response);
    }

    @Override
    public GetResultsForTestSetsOfAllCommitsResponse getResultsForAllCommitsInBranch(GetResultsForTestSetsOfAllCommitsRequest request) {
        List<Object[]> results = commitDal.getResultsForAllCommitsInBranch(request.getBranchId());

        Map<Commit, Integer> response = new HashMap<>();
        for (Object[] result : results) {
            Commit commit = CommitConverter.convert((CommitEntity) result[0]);
            Integer successFlag = (Integer) result[1];
            response.put(commit, successFlag);
        }

        return new GetResultsForTestSetsOfAllCommitsResponse(response);
    }

    @Override
    public GetResultsForAllCommitsOfBranchWithIdResponse getResultsForAllCommitsInBranchOrdered(GetResultsForTestSetsOfAllCommitsRequest request) {
        List<Object[]> results = commitDal.getResultsForAllCommitsInBranch(request.getBranchId());

        TreeMap<Commit, Integer> response = new TreeMap<>(new CommitComparator());
        for (Object[] result : results) {
            Commit commit = CommitConverter.convert((CommitEntity) result[0]);
            Integer successFlag = (Integer) result[1];
            response.put(commit, successFlag);
        }

        return new GetResultsForAllCommitsOfBranchWithIdResponse(response);
    }

    @Override
    public Map<Integer, Integer> getResultsForAllCommitsInBranchForReact(GetResultsForTestSetsOfAllCommitsRequest request) {
        List<Object[]> results = commitDal.getResultsForAllCommitsInBranch(request.getBranchId());

        Map<Integer, Integer> response = new HashMap<>();
        for (Object[] result : results) {
            Integer commitId = ((CommitEntity) result[0]).getId();
            Integer successFlag = (Integer) result[1];
            response.put(commitId, successFlag);
        }

        return response;
    }

    // FILTERS BELOW ------------------------------------------
    @Override
    public GetCommitsResponse getCommitWithErrorOfBranchWithId(GetCommitsWithTestErrorsInBranchRequest request) {
        Map<Integer, Commit> response = new HashMap<>();
        List<Object[]> results = commitDal.getCommitWithErrorOfBranchWithId(request.getBranchId(),
                request.getErrorId());
        for (Object[] result : results) {
            CommitEntity commEntity = new CommitEntity().builder()
                    .id((Integer) result[0])
                    .name(result[1].toString())
                    .originBranch((BranchEntity.builder().id((int) result[2]).build()))
                    .version(result[3].toString())
                    .versionDate((Timestamp) result[4])
                    .build();
            //Integer successFlag = (Integer) result[0];
            Commit commit = CommitConverter.convert(commEntity);

            response.put(commit.getId(), commit);
        }
        return new GetCommitsResponse(response);
    }

    @Override
    public GetCommitsResponse getCommitsWithTestResultInBranchRequest(GetCommitsWithTestResultInBranchRequest request) {
        Map<Integer, Commit> response = new HashMap<>();
        List<Object[]> results = commitDal.getCommitsWithTestResultInBranchRequest(request.getResultId(), request.getBranchId()
        );
        for (Object[] result : results) {
            CommitEntity commEntity = new CommitEntity().builder()
                    .id((Integer) result[0])
                    .name(result[1].toString())
                    .originBranch((BranchEntity.builder().id((int) result[2]).build()))
                    .version(result[3].toString())
                    .versionDate((Timestamp) result[4])
                    .build();
            //Integer successFlag = (Integer) result[0];
            Commit commit = CommitConverter.convert(commEntity);

            response.put(commit.getId(), commit);
        }
        return new GetCommitsResponse(response);
    }

    @Override
    public GetCommitsResponse getCommitWithVersionOfBranchWithId(GetCommitsWithVersionInBranchRequest request) {
        Map<Integer, Commit> response = new HashMap<>();
        List<Object[]> results = commitDal.getCommitWithVersionOfBranchWithId(request.getBranchId(),
                request.getVersion());
        for (Object[] result : results) {
            CommitEntity commEntity = new CommitEntity().builder()
                    .id((Integer) result[0])
                    .name(result[1].toString())
                    .originBranch((BranchEntity.builder().id((int) result[2]).build()))
                    .version(result[3].toString())
                    .versionDate((Timestamp) result[4])
                    .build();
            //Integer successFlag = (Integer) result[0];
            Commit commit = CommitConverter.convert(commEntity);

            response.put(commit.getId(), commit);
        }
        return new GetCommitsResponse(response);
    }

    @Override
    public GetCommitsResponse getAllCommitsFromAllBranchesOfUserWithId(Integer userId) {
        Map<Integer, Commit> response = new HashMap<>();
        List<CommitEntity> results = commitDal.getAllCommitsPostedByUserWithId(userId);
        results.forEach(result -> response.put(result.getId(),
                CommitConverter.convert(result)));
        return new GetCommitsResponse(response);
    }

    @Override
    public Integer getCountCommitsOfBranchWithId(GetCommitsRequest request) {
        Map<Integer, Integer> counts = new HashMap<>();

         Integer response = commitDal.getCountCommitsOfBranchWithId(request.getBranchId());
         return response;
    }

    @Override
    public Map<Integer, Integer> getTestSetsNumberOfCommitsInBranchWithId(Integer id) {
        Map<Integer, Integer> counts = new HashMap<>();

        GetCommitsRequest request = GetCommitsRequest.builder().branchId(id).build();
        GetCommitsResponse commitsOfBranch = this.getAllCommitsOfBranch(request);

        for (Map.Entry<Integer, Commit> entryCommit : commitsOfBranch.getCommits().entrySet())
        {
            GetTestSetsRequest testSetsRequest = GetTestSetsRequest.builder().commitId(entryCommit.getValue().getId()).build();
            Integer testSetsNumber = testSetService.getTestSetsOfCommitWithId(testSetsRequest).getTestSets().size();
            counts.put(entryCommit.getValue().getId(), testSetsNumber);
        }
        return counts;
    }


    private static class CommitComparator implements Comparator<Commit> {
        @Override
        public int compare(Commit commit1, Commit commit2) {
            return commit1.getVersionDate().compareTo(commit2.getVersionDate());
        }
    }
}
