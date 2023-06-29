package s3.algorithalliance.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3.algorithalliance.domain.reqresp.testres.*;
import s3.algorithalliance.servicelayer.ITestService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/testresults")
@AllArgsConstructor
public class TestController {
    private ITestService testService;

    @GetMapping("{testId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<GetTestResultResponse> getTest(@PathVariable(name =
            "testId") Integer testId) {
        GetTestResultRequest request = new GetTestResultRequest(testId);
        GetTestResultResponse response = testService.getTestResult(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("testSet/{testSetId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<GetTestResultsResponse> getTestResultsOfTestSetWithId(
            @PathVariable(name = "testSetId") Integer testSetId) {
        GetTestResultsRequest request = new GetTestResultsRequest(testSetId);
        GetTestResultsResponse response =
                testService.getTestResultsOfTestSetWithId(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{testId}/count")
    public ResponseEntity<GetSubtestsCountResponse> getNumberOfSubtestsOfTest(
            @PathVariable(name = "testId") Integer testId) {
        GetSubtestsCountRequest request = new GetSubtestsCountRequest(testId);
        GetSubtestsCountResponse response =
                testService.getSubtestsOfTestWithId(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{testId}/results")
    public ResponseEntity<GetSubtestsResultsInTestResponse> getResultsOfSubtestsOfTest(
            @PathVariable(name = "testId") Integer testId) {
        GetSubtestsResultsInTestRequest request = new GetSubtestsResultsInTestRequest(testId);
        GetSubtestsResultsInTestResponse response = testService.getSubtestsResultsInTestWithId(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{testId}/count/passed")
    public ResponseEntity<GetSubtestsCountResponse> getNumberOfPassedSubtestsOfTest(
            @PathVariable(name = "testId") Integer testId) {
        GetSubtestsCountRequest request = new GetSubtestsCountRequest(testId);
        GetSubtestsCountResponse response =
                testService.getSubtestsOfTestWithId(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateTestResultResponse> createTestResult(@RequestBody @Valid CreateTestResultRequest request) {
        CreateTestResultResponse response = testService.createTestResult(request);
        return ResponseEntity.ok(response);
    }

    // FILTERS BELOW

    @GetMapping("testSet/{testSetId}/results/{resultId}")
    public ResponseEntity<GetTestResultsResponse> getTestsWithResultOfTestSet(
            @PathVariable(name = "testSetId") Integer testSetId, @PathVariable(name = "resultId") Integer resultId) {
        GetTestWithTestResultsOfTestSetRequest request = new GetTestWithTestResultsOfTestSetRequest(testSetId, resultId);
        GetTestResultsResponse response =
                testService.getTestsWithResultOfTestSet(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("testSet/{testSetId}/errors/{errorId}")
    public ResponseEntity<GetTestResultsResponse> getTestsWithErrorOfTestSet(
            @PathVariable(name = "testSetId") Integer testSetId, @PathVariable(name = "errorId") Integer errorId) {
        GetTestWithTestErrorsOfTestSetRequest request = new GetTestWithTestErrorsOfTestSetRequest(testSetId, errorId);
        GetTestResultsResponse response =
                testService.getTestsWithErrorOfTestSet(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/testSet/{testSetId}/count")
    public ResponseEntity<Integer> getCountTestsOfTestSetWithId(
            @PathVariable(name = "testSetId") Integer testSetId) {
        GetTestResultsRequest request = new GetTestResultsRequest(testSetId);
        return ResponseEntity.ok(testService.getCountTestsOfTestSetWithId(request));
    }


    // Alessandro COUNT
    @GetMapping("/testSets/{testSetId}/count-new")
    public Map<Integer, Integer> getSubtestsNumberOfTestInTestSetWithId(@PathVariable(name = "testSetId") Integer testSetId) {
        Map<Integer, Integer> response = testService.getSubtestsNumberOfTestInTestSetWithId(testSetId);
        return response;
    }

}