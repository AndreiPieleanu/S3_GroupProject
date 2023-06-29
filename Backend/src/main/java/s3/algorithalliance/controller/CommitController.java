package s3.algorithalliance.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3.algorithalliance.configuration.security.isauthenticated.IsAuthenticated;
import s3.algorithalliance.domain.Commit;
import s3.algorithalliance.domain.reqresp.commit.*;
import s3.algorithalliance.domain.reqresp.testset.GetTestSetsRequest;
import s3.algorithalliance.servicelayer.ICommitService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/commits")
@AllArgsConstructor
public class CommitController {
    private ICommitService commitService;
    @GetMapping("{commitId}")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<GetCommitResponse> getCommit(@PathVariable(name =
            "commitId") Integer commitId) {
        GetCommitRequest request = new GetCommitRequest(commitId);
        GetCommitResponse response = commitService.getCommit(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("{commitId}/response")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<GetTestSetsResultsInCommitResponse> getTestSetsResultsBasedOnCommitWithId(@PathVariable(name =
            "commitId") Integer commitId) {
        GetTestSetsResultsInCommitRequest request = new GetTestSetsResultsInCommitRequest(commitId);
        GetTestSetsResultsInCommitResponse response = commitService.getTestSetsResultsInCommitWithId(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<GetCommitsResponse> getCommitsOfBranchWithId(@PathVariable(name = "branchId") Integer branchId) {
        GetCommitsRequest request = new GetCommitsRequest(branchId);
        GetCommitsResponse response = commitService.getAllCommitsOfBranch(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branch/{branchId}/response")
    public ResponseEntity<Map<Integer, Integer>> getResultsForAllCommitsInBranch(@PathVariable(name = "branchId") Integer branchId) {
        GetResultsForTestSetsOfAllCommitsRequest request = new GetResultsForTestSetsOfAllCommitsRequest(branchId);
        Map<Integer, Integer> response = commitService.getResultsForAllCommitsInBranchForReact(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateCommitResponse> createCommit(@RequestBody @Valid CreateCommitRequest request) {
        CreateCommitResponse response = commitService.createCommit(request);
        return ResponseEntity.ok(response);
    }
    
    @IsAuthenticated
    @RolesAllowed({"ROLE_USER"})
    @GetMapping("/all/users/{userId}")
    public ResponseEntity<GetCommitsResponse> getAllCommitsFromAllBranchesOfUserWithId(@PathVariable(name = "userId") Integer userId){
        GetCommitsResponse response =
                commitService.getAllCommitsFromAllBranchesOfUserWithId(userId);
        return ResponseEntity.ok(response);
    }

    // FILTERS
    @GetMapping("/branch/{branchId}/errors/{errorId}")
    public ResponseEntity<GetCommitsResponse> getCommitsWithErrorOfBranch(@PathVariable(name = "branchId") Integer branchId,
                                                                          @PathVariable(name="errorId") Integer errorId) {
        GetCommitsWithTestErrorsInBranchRequest request = new GetCommitsWithTestErrorsInBranchRequest(branchId,errorId);
        GetCommitsResponse response = commitService.getCommitWithErrorOfBranchWithId(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/branch/{branchId}/results/{resultId}")
    public ResponseEntity<GetCommitsResponse> getCommitsWithTestResultInBranchRequest(@PathVariable(name = "branchId") Integer branchId,
                                                                                      @PathVariable(name="resultId") Integer resultId) {
        GetCommitsWithTestResultInBranchRequest request = new GetCommitsWithTestResultInBranchRequest(branchId,resultId);
        GetCommitsResponse response = commitService.getCommitsWithTestResultInBranchRequest(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/branch/{branchId}/version/{version}")
    public ResponseEntity<GetCommitsResponse> getCommitsWithVersionInBranchRequest(@PathVariable(name = "branchId") Integer branchId,
                                                                                   @PathVariable(name="version") String version) {
        GetCommitsWithVersionInBranchRequest request = new GetCommitsWithVersionInBranchRequest(branchId,version);
        GetCommitsResponse response = commitService.getCommitWithVersionOfBranchWithId(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/branch/{branchId}/count")
    public ResponseEntity<Integer> getCountCommitsOfBranchWithId(@PathVariable(name = "branchId") Integer branchId) {
        GetCommitsRequest request = new GetCommitsRequest(branchId);
        Integer response = commitService.getCountCommitsOfBranchWithId(request);
        return ResponseEntity.ok(response);
    }

    // Alessandro COUNT
    @GetMapping("/branch/{branchId}/count-new")
    public Map<Integer, Integer> getTestSetsNumberOfCommitsInBranchWithId(@PathVariable(name = "branchId") Integer branchId) {
        Map<Integer, Integer> response = commitService.getTestSetsNumberOfCommitsInBranchWithId(branchId);
        return response;
    }
}
