package s3.algorithalliance.servicelayer.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import s3.algorithalliance.datalayer.IBranchDal;
import s3.algorithalliance.datalayer.entities.BranchEntity;
import s3.algorithalliance.datalayer.entities.UserEntity;
import s3.algorithalliance.domain.Branch;
import s3.algorithalliance.domain.Commit;
import s3.algorithalliance.domain.CommitError;
import s3.algorithalliance.domain.PieChartStats;
import s3.algorithalliance.domain.converters.BranchConverter;
import s3.algorithalliance.domain.reqresp.branches.*;
import s3.algorithalliance.domain.reqresp.commit.GetCommitsRequest;
import s3.algorithalliance.domain.reqresp.commit.GetCommitsResponse;
import s3.algorithalliance.domain.reqresp.commit.GetResultsForTestSetsOfAllCommitsRequest;
import s3.algorithalliance.servicelayer.IBranchService;
import s3.algorithalliance.servicelayer.ICommitService;
import s3.algorithalliance.servicelayer.exc.BranchNotFoundException;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.*;

@Service
@AllArgsConstructor
public class BranchService implements IBranchService {
    IBranchDal branchDal;
    ICommitService commitService;

    @Override
    public GetPublicBranchesResponse getAllPublicBranches() {
        List<BranchEntity> publicBranches = branchDal.findAllPublicBranches();
        Map<Integer, Branch> convertedBranches = new HashMap<>();
        publicBranches.forEach(v -> convertedBranches.put(v.getId(), BranchConverter.convert(v)));
        return new GetPublicBranchesResponse(convertedBranches);
    }

    @Override
    public GetUserBranchesResponse getBranchesOfUser(GetBranchRequest request) {
        Map<Integer, Branch> userBranches = new HashMap<>();
        branchDal.findAllBranchesForUserWithId(request.getUserId()).forEach(br -> userBranches.put(br.getId(), BranchConverter.convert(br)));
        return new GetUserBranchesResponse(userBranches);
    }

    @Override
    public GetBranchResponse getBranch(GetBranchRequest request) {
        BranchEntity foundBranch = branchDal.findById(request.getUserId()).orElseThrow(BranchNotFoundException::new);
        Branch convertedBranch = BranchConverter.convert(foundBranch);
        return new GetBranchResponse(convertedBranch);
    }

    @Override
    public CreateBranchResponse createBranch(CreateBranchRequest request) {
        UserEntity foundUser = null;
        if(request.getUserId() != null){
            foundUser =
                    UserEntity.builder().id(request.getUserId()).build();
        }
        BranchEntity branchToAdd = BranchEntity
                .builder()
                .name(request.getName())
                .userEntity(foundUser)
                .commits(new HashMap<>())
                .build();
        BranchEntity createdBranch = branchDal.save(branchToAdd);
        return new CreateBranchResponse(createdBranch.getId());
    }

    @Override
    public List<CommitError> getCommitErrors() {
        List<CommitError> response = new ArrayList<CommitError>();

        for (Map.Entry<Integer, Branch> entryBranch : this.getAllPublicBranches().getPublicBranches().entrySet())
        {
            Commit foundLastCommit = Commit.builder().versionDate(new Timestamp(0,0,0,0,0,0,0)).build();
            Integer foundLastCommitResult = 1;
            for (Map.Entry<Commit, Integer> entry : commitService.getResultsForAllCommitsInBranchOrdered(GetResultsForTestSetsOfAllCommitsRequest.builder().branchId(entryBranch.getValue().getId()).build()).getResults().entrySet())
            {
                if (entry.getKey().getVersionDate().after(foundLastCommit.getVersionDate())) {
                    foundLastCommit = entry.getKey();
                    foundLastCommitResult = entry.getValue();
                }
            }
            if (foundLastCommitResult.equals(0)) {
                Commit failed = null;
                for (Map.Entry<Commit, Integer> entry : commitService.getResultsForAllCommitsInBranchOrdered(GetResultsForTestSetsOfAllCommitsRequest.builder().branchId(entryBranch.getValue().getId()).build()).getResults().entrySet())
                {
                    if (!foundLastCommit.equals(entry.getKey()))
                    {
                        if (entry.getValue().equals(1) && !Objects.isNull(failed)) {
                            // Reset to null because in a more recent commit it has been fixed
                            failed = null;
                        }
                        else if (entry.getValue().equals(0) && Objects.isNull(failed))
                        {
                            // Set failed as the current commit
                            failed = entry.getKey();
                        }
                    }
                    else {
                        if (Objects.isNull(failed)) {
                            failed = foundLastCommit;
                        }
                    }
                }
                response.add(
                        CommitError.builder()
                                .branch(entryBranch.getValue())
                                .errorStartCommit(failed)
                                .lastCommit(foundLastCommit)
                                .build()
                );
                // This above is the logic to loop through the Map again and check
                // which commit started to have result 0. Please note, if a more recent commit
                // has result 1, all the commits that failed before should not be taken care of.
                // Previous commits can be identified using the map key commit.getVersionDate()
                // which is a Timestamp object. But in this code we assume that the Map is already
                // ordered by date.
            }
        }
        return response;
    }

    @Override
    public List<CommitError> getCommitErrorsForAuthedUser(Integer userId) {
        List<CommitError> response = new ArrayList<CommitError>();

        for (Map.Entry<Integer, Branch> entryBranch : this.getBranchesOfUser(GetBranchRequest.builder().userId(userId).build()).getUserBranches().entrySet())
        {
            Commit foundLastCommit = Commit.builder().versionDate(new Timestamp(0,0,0,0,0,0,0)).build();
            Integer foundLastCommitResult = 1;
            for (Map.Entry<Commit, Integer> entry : commitService.getResultsForAllCommitsInBranchOrdered(GetResultsForTestSetsOfAllCommitsRequest.builder().branchId(entryBranch.getValue().getId()).build()).getResults().entrySet())
            {
                if (entry.getKey().getVersionDate().after(foundLastCommit.getVersionDate())) {
                    foundLastCommit = entry.getKey();
                    foundLastCommitResult = entry.getValue();
                }
            }
            if (foundLastCommitResult.equals(0)) {
                Commit failed = null;
                // Map map = commitService.getResultsForAllCommitsInBranchOrdered(GetResultsForTestSetsOfAllCommitsRequest.builder().branchId(entryBranch.getValue().getId()).build()).getResults();
                for (Map.Entry<Commit, Integer> entry : commitService.getResultsForAllCommitsInBranchOrdered(GetResultsForTestSetsOfAllCommitsRequest.builder().branchId(entryBranch.getValue().getId()).build()).getResults().entrySet())
                {
                    System.out.println(foundLastCommitResult);
                    System.out.println(entry.getKey());
                    if (!foundLastCommit.equals(entry.getKey()))
                    {
                        if (entry.getValue().equals(1) && !Objects.isNull(failed)) {
                            // Reset to null because in a more recent commit it has been fixed
                            failed = null;
                        }
                        else if (entry.getValue().equals(0) && Objects.isNull(failed))
                        {
                            // Set failed as the current commit
                            failed = entry.getKey();
                        }
                    }
                    else {
                        if (Objects.isNull(failed)) {
                            failed = foundLastCommit;
                        }
                    }
                }
                response.add(
                        CommitError.builder()
                                .branch(entryBranch.getValue())
                                .errorStartCommit(failed)
                                .lastCommit(foundLastCommit)
                                .build()
                );
                // This above is the logic to loop through the Map again and check
                // which commit started to have result 0. Please note, if a more recent commit
                // has result 1, all the commits that failed before should not be taken care of.
                // Previous commits can be identified using the map key commit.getVersionDate()
                // which is a Timestamp object. But in this code we assume that the Map is already
                // ordered by date.
            }
        }
        return response;
    }

    @Override
    public Map getBranchResults() {

        Map<Integer, Integer> response = new HashMap<>();

        for (Map.Entry<Integer, Branch> entryBranch : this.getAllPublicBranches().getPublicBranches().entrySet())
        {
            TreeMap<Commit, Integer> results = commitService.getResultsForAllCommitsInBranchOrdered(GetResultsForTestSetsOfAllCommitsRequest.builder().branchId(entryBranch.getKey()).build()).getResults();
            if (!results.isEmpty()) {
                Integer lastEntryResult = results.lastEntry().getValue();
                response.put(entryBranch.getKey(), lastEntryResult);
            }
        }
        return response;
    }

    @Override
    public Map getBranchResultsForAuthed(Integer userId) {

        Map<Integer, Integer> response = new HashMap<>();

        for (Map.Entry<Integer, Branch> entryBranch : this.getBranchesOfUser(GetBranchRequest.builder().userId(userId).build()).getUserBranches().entrySet())
        {
            TreeMap<Commit, Integer> results = commitService.getResultsForAllCommitsInBranchOrdered(GetResultsForTestSetsOfAllCommitsRequest.builder().branchId(entryBranch.getKey()).build()).getResults();
            if (!results.isEmpty()) {
                Integer lastEntryResult = results.lastEntry().getValue();
                response.put(entryBranch.getKey(), lastEntryResult);
            }
        }
        return response;
    }

    @Override
    public Map<Integer, String> getLastVersionOfBranches() {

        Map<Integer, String> response = new HashMap<>();

        for (Map.Entry<Integer, Branch> entryBranch : this.getAllPublicBranches().getPublicBranches().entrySet())
        {
            TreeMap<Commit, Integer> results = commitService.getResultsForAllCommitsInBranchOrdered(GetResultsForTestSetsOfAllCommitsRequest.builder().branchId(entryBranch.getKey()).build()).getResults();
            if (!results.isEmpty()) {
                String lastEntryVersion = results.lastEntry().getKey().getVersion();
                response.put(entryBranch.getKey(), lastEntryVersion);
            }
        }
        return response;
    }

    @Override
    public Map<Integer, String> getLastVersionOfBranchesAuthed(Integer userId) {

        Map<Integer, String> response = new HashMap<>();

        for (Map.Entry<Integer, Branch> entryBranch : this.getBranchesOfUser(GetBranchRequest.builder().userId(userId).build()).getUserBranches().entrySet())
        {
            TreeMap<Commit, Integer> results = commitService.getResultsForAllCommitsInBranchOrdered(GetResultsForTestSetsOfAllCommitsRequest.builder().branchId(entryBranch.getKey()).build()).getResults();
            if (!results.isEmpty()) {
                String lastEntryVersion = results.lastEntry().getKey().getVersion();
                response.put(entryBranch.getKey(), lastEntryVersion);
            }
        }
        return response;
    }

    @Override
    public PieChartStats getStats() {
        Integer passed = 0;
        Integer total = 0;
        for (Map.Entry<Integer, Branch> entryBranch : this.getAllPublicBranches().getPublicBranches().entrySet())
        {
            TreeMap<Commit, Integer> results = commitService.getResultsForAllCommitsInBranchOrdered(GetResultsForTestSetsOfAllCommitsRequest.builder().branchId(entryBranch.getKey()).build()).getResults();
            if (!results.isEmpty()) {
                for (Map.Entry<Commit, Integer> entry : results.entrySet()) {
                    if (entry.getValue().equals(1)) {
                        passed = passed + 1;
                    }
                    total = total + 1;
                }
            }
        }
        return new PieChartStats(passed, total);
    }

    @Override
    public PieChartStats getStatsAuthed(Integer userId) {
        Integer passed = 0;
        Integer total = 0;
        for (Map.Entry<Integer, Branch> entryBranch : this.getBranchesOfUser(GetBranchRequest.builder().userId(userId).build()).getUserBranches().entrySet())
        {
            TreeMap<Commit, Integer> results = commitService.getResultsForAllCommitsInBranchOrdered(GetResultsForTestSetsOfAllCommitsRequest.builder().branchId(entryBranch.getKey()).build()).getResults();
            if (!results.isEmpty()) {
                for (Map.Entry<Commit, Integer> entry : results.entrySet()) {
                    if (entry.getValue().equals(1)) {
                        passed = passed + 1;
                    }
                    total = total + 1;
                }
            }
        }
        return new PieChartStats(passed, total);
    }
}
