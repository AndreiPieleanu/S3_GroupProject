package s3.algorithalliance.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3.algorithalliance.domain.reqresp.testset.*;
import s3.algorithalliance.servicelayer.ITestSetService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/testSets")
@AllArgsConstructor
public class TestSetController {
    private ITestSetService testSetService;

    @GetMapping("/{testSetId}")
    public ResponseEntity<GetTestSetResponse> getTestSet(@PathVariable(name =
            "testSetId") Integer testSetId) {
        GetTestSetRequest request = new GetTestSetRequest(testSetId);
        GetTestSetResponse response = testSetService.getTestSet(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/commits/{commitId}")
    public ResponseEntity<GetTestSetsResponse> getTestSetsForCommitWithId(@PathVariable(name = "commitId") Integer commitId) {
        GetTestSetsRequest request = new GetTestSetsRequest(commitId);
        GetTestSetsResponse response = testSetService.getTestSetsOfCommitWithId(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{testSetId}/count")
    public ResponseEntity<GetTestCountResponse> getNumberOfTestsOfTestSetWithId(@PathVariable(name =
            "testSetId") Integer testSetId) {
        GetTestCountRequest request = new GetTestCountRequest(testSetId);
        GetTestCountResponse response = testSetService.getCountOfAllTestResultsOfTestSetWithId(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{testSetId}/count/passed")
    public ResponseEntity<GetTestCountResponse> getNumberOfPassedTestsOfTestSetWithId(@PathVariable(name =
            "testSetId") Integer testSetId) {
        GetTestCountRequest request = new GetTestCountRequest(testSetId);
        GetTestCountResponse response = testSetService.getCountOfSuccessfulTestsOfTestSetWithId(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{testSetId}/results")
    public ResponseEntity<GetTestResultsInTestSetResponse> getResultsForTestsOfTestSetWithId(@PathVariable(name =
            "testSetId") Integer testSetId) {
        GetTestResultsInTestSetRequest request = new GetTestResultsInTestSetRequest(testSetId);
        GetTestResultsInTestSetResponse response = testSetService.getTestResultsInTestSetWithId(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping()
    public ResponseEntity<GetTestSetsResponse> getTestSetsOfCommitWithId(@RequestBody @Valid GetTestSetsRequest request) {
        GetTestSetsResponse response = testSetService.getTestSetsOfCommitWithId(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateTestSetResponse> createTestSet(@RequestBody @Valid CreateTestSetRequest request) {
        CreateTestSetResponse response = testSetService.createTestSet(request);
        return ResponseEntity.ok(response);
    }

    //filters
    @GetMapping("/commits/{commitId}/errors/{errorId}")
    public ResponseEntity<GetTestSetsResponse> getTestSetsWithTestErrorsOfCommitWithId(@PathVariable(name = "commitId") Integer commitId, @PathVariable(name = "errorId") Integer errorId) {
        GetTestSetsWithTestErrorsInCommitRequest request = new GetTestSetsWithTestErrorsInCommitRequest(commitId,errorId);
        GetTestSetsResponse response = testSetService.getTestSetsWithErrorOfCommit(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/commits/{commitId}/results/{resultId}")
    public ResponseEntity<GetTestSetsResponse> getTestSetsWithTestResultsOfCommitWithId(@PathVariable(name = "commitId") Integer commitId, @PathVariable(name = "resultId") Integer resultId) {
        GetTestSetsWithTestResultsInCommitRequest request = new GetTestSetsWithTestResultsInCommitRequest(commitId,resultId);
        GetTestSetsResponse response = testSetService.getTestSetsWithResultsOfCommit(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/commits/{commitId}/count")
    public ResponseEntity<Integer> getCountTestSetsOfCommitWithId(@PathVariable(name = "commitId") Integer commitId) {
        GetTestSetsRequest request = new GetTestSetsRequest(commitId);
        Integer response = testSetService.getCountTestSetsOfCommitWithId(request);
        return ResponseEntity.ok(response);
    }

    // Alessandro COUNT
    @GetMapping("/commits/{commitId}/count-new")
    public Map<Integer, Integer> getTestsNumberOfTestSetInCommitWithId(@PathVariable(name = "commitId") Integer commitId) {
        Map<Integer, Integer> response = testSetService.getTestsNumberOfTestSetInCommitWithId(commitId);
        return response;
    }
}
