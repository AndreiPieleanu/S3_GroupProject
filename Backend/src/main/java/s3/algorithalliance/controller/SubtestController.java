package s3.algorithalliance.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import s3.algorithalliance.domain.reqresp.subtest.*;
import s3.algorithalliance.domain.reqresp.teststep.GetTestStepsRequest;
import s3.algorithalliance.servicelayer.ISubtestService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/subtests")
@AllArgsConstructor
public class SubtestController {
    private ISubtestService subtestService;
    @GetMapping("{subTestId}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<GetSubtestResponse> getSubtest(@PathVariable(name =
            "subTestId") Integer subTestId){
        GetSubtestRequest request = new GetSubtestRequest(subTestId);
        GetSubtestResponse response = subtestService.getSubtest(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/test/{testId}")
    public ResponseEntity<GetSubtestsResponse> getSubTestsOfTestWithId(
            @PathVariable(name = "testId") Integer testId) {
        GetSubtestsRequest request = new GetSubtestsRequest(testId);
        GetSubtestsResponse response =
                subtestService.getSubtestsOfTestWithId(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("{subTestId}/count")
    public ResponseEntity<GetTestStepsCountResponse> getTestStepsCountResponseResponseEntity(@PathVariable(name = "subTestId") Integer subtestId){
        GetTestStepsCountRequest request = new GetTestStepsCountRequest(subtestId);
        GetTestStepsCountResponse response = subtestService.getTestStepsCountOfSubtestWithId(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("{subTestId}/count/passed")
    public ResponseEntity<GetTestStepsCountResponse> getSuccessfulTestStepsOfSubtestWithId(@PathVariable(name = 
            "subTestId") Integer subtestId){
        GetTestStepsCountRequest request = new GetTestStepsCountRequest(subtestId);
        GetTestStepsCountResponse response = subtestService.getSuccessfulTestStepsOfSubtestWithId(request);
        return ResponseEntity.ok(response);
    }
    @PostMapping
    public ResponseEntity<CreateSubtestResponse> createSubtest(@RequestBody @Valid CreateSubtestRequest request){
        CreateSubtestResponse response = subtestService.createSubtest(request);
        return ResponseEntity.ok(response);
    }


    // FILTERS BELOW

    @GetMapping("/test/{testId}/errors/{errorId}")
    public ResponseEntity<GetSubtestsResponse> getSubTestsWithErrorOfTest(
            @PathVariable(name = "testId") Integer testId,@PathVariable(name = "errorId") Integer errorId) {
        GetSUbTestWithTestErrorsOfTestRequest request = new GetSUbTestWithTestErrorsOfTestRequest(testId,errorId);
        GetSubtestsResponse response =
                subtestService.getSubTestsWithErrorOfTest(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/test/{testId}/results/{resultId}")
    public ResponseEntity<GetSubtestsResponse> getSubTestsWithResultOfTest(
            @PathVariable(name = "testId") Integer testId,@PathVariable(name = "resultId") Integer resultId) {
        GetSubTestWithTestResultsOfTestRequest request = new GetSubTestWithTestResultsOfTestRequest(testId,resultId);
        GetSubtestsResponse response =
                subtestService.getSubTestsWithResultOfTest(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/test/{testId}/count")
    public ResponseEntity<Integer> getCountSubTestsOfTestWithId(
            @PathVariable(name = "testId") Integer testId) {
        GetSubtestsRequest request = new GetSubtestsRequest(testId);
        return ResponseEntity.ok(subtestService.getCountSubTestsOfTestWithId(request));
    }

    // Alessandro COUNT
    @GetMapping("/tests/{testId}/count-new")
    public Map<Integer, Integer> getTestStepsNumberOfSubestInTestWithId(@PathVariable(name = "testId") Integer testId) {
        Map<Integer, Integer> response = subtestService.getTestStepsNumberOfSubestInTestWithId(testId);
        return response;
    }


}
