package s3.algorithalliance.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3.algorithalliance.configuration.security.isauthenticated.IsAuthenticated;
import s3.algorithalliance.domain.CommitError;
import s3.algorithalliance.domain.PieChartStats;
import s3.algorithalliance.domain.reqresp.branches.*;
import s3.algorithalliance.domain.reqresp.user.CreateUserRequest;
import s3.algorithalliance.servicelayer.IBranchService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/branches")
@AllArgsConstructor
public class BranchController {
    private IBranchService branchService;
    @GetMapping("/public")
    public ResponseEntity<GetPublicBranchesResponse> getAllPublicBranches(){
        GetPublicBranchesResponse response = branchService.getAllPublicBranches();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/users/{userId}")
    @IsAuthenticated
    @RolesAllowed({"ROLE_USER"})
    public ResponseEntity<GetUserBranchesResponse> getAllBranchesForUserWithId(@PathVariable(value = "userId")Integer userId){
        GetBranchRequest request = new GetBranchRequest(userId);
        GetUserBranchesResponse response = branchService.getBranchesOfUser(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<CreateBranchResponse> createBranch(@RequestBody @Valid CreateBranchRequest request){
        CreateBranchResponse response = branchService.createBranch(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{branchId}")
    public ResponseEntity<GetBranchResponse> getBranchById(@PathVariable(value = "branchId")Integer branchId){
        GetBranchRequest request = new GetBranchRequest(branchId);
        GetBranchResponse response = branchService.getBranch(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-commit-errors")
    public ResponseEntity<List<CommitError>> getCommitErrors() {
        List<CommitError> response = branchService.getCommitErrors();
        return ResponseEntity.ok(response);
    }

    @IsAuthenticated
    @RolesAllowed({"ROLE_USER"})
    @GetMapping("/get-commit-errors-authed/{userId}")
    public ResponseEntity<List<CommitError>> getCommitErrorsForUserWithId(@PathVariable(value = "userId")Integer userId) {
        List<CommitError> response = branchService.getCommitErrorsForAuthedUser(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-branch-results")
    public ResponseEntity<Map> getBranchResults() {
        Map response = branchService.getBranchResults();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-branch-results/{userId}")
    public ResponseEntity<Map> getBranchResults(@PathVariable(value = "userId")Integer userId) {
        Map response = branchService.getBranchResultsForAuthed(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-last-version")
    public ResponseEntity<Map> getLastVersionOfBranches() {
        Map response = branchService.getLastVersionOfBranches();
        return ResponseEntity.ok(response);
    }


    @IsAuthenticated
    @RolesAllowed({"ROLE_USER"})
    @GetMapping("/get-last-version/{userId}")
    public ResponseEntity<Map> getLastVersionOfBranches(@PathVariable(value = "userId")Integer userId) {
        Map response = branchService.getLastVersionOfBranchesAuthed(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-stats")
    public ResponseEntity<PieChartStats> getStats() {
        PieChartStats response = branchService.getStats();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-stats/{userId}")
    public ResponseEntity<PieChartStats> getStats(@PathVariable(value = "userId")Integer userId) {
        PieChartStats response = branchService.getStatsAuthed(userId);
        return ResponseEntity.ok(response);
    }
}
