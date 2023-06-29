package s3.algorithalliance.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import s3.algorithalliance.domain.reqresp.teststep.*;
import s3.algorithalliance.servicelayer.ITestStepService;

import javax.validation.Valid;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/teststeps")
@AllArgsConstructor
public class TestStepController {
    private ITestStepService testStepService;
    @GetMapping("/subtest/{subTestId}/count")
    public int getTestStepsCountOfSubTestWithId(@PathVariable(name = "subTestId") Integer subTestId) {
       GetTestStepsRequest request = new GetTestStepsRequest(subTestId);
        return testStepService.getCountTestStepsOfSubtestWithId(request);
    }

    @GetMapping("{setsStepId}")
    public ResponseEntity<GetTestStepResponse> getSubtest(@PathVariable(name =
            "setsStepId") Integer setsStepId) {
        GetTestStepRequest request = new GetTestStepRequest(setsStepId);
        GetTestStepResponse response = testStepService.getTestStep(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("subtest/{subTestId}")
    public ResponseEntity<GetTestStepsResponse> getTestStepsOfSubTestWithId(@PathVariable(name = "subTestId") Integer subTestId) {
        GetTestStepsRequest request = new GetTestStepsRequest(subTestId);
        GetTestStepsResponse response =
                testStepService.getAllTestStepsOfSubtestWithId(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateTestStepResponse> createCommit(@RequestBody @Valid CreateTestStepRequest request) {
        CreateTestStepResponse response = testStepService.createTestResponse(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<GetTestStepsByErrorResponse> getTestStepsByErrorOfSubTestWithId(@RequestParam(name = "errorId") Integer errorId,
                                                                           @RequestParam(name = "subTestId") Integer subTestId) {
        GetTestStepsByErrorRequest request = new GetTestStepsByErrorRequest(errorId, subTestId);
        GetTestStepsByErrorResponse response = testStepService.getTestStepsByError(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/error_type/{errorId}/subtests/{subTestId}")
    public ResponseEntity<GetTestStepsByErrorResponse> getTestStepsByErrorOfSubTestWithId_UPDATE(@PathVariable(name = "errorId") Integer errorId,
                                                                                          @PathVariable(name = "subTestId") Integer subTestId) {
        GetTestStepsByErrorRequest request = new GetTestStepsByErrorRequest(errorId, subTestId);
        GetTestStepsByErrorResponse response = testStepService.getTestStepsByError(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/result_type/{resultId}")
    public ResponseEntity<GetTestStepsResponse> getAllTestStepsWithResultId(@PathVariable(name = "resultId") Integer resultId) {
        GetTestStepRequest request = new GetTestStepRequest(resultId);
        GetTestStepsResponse response = testStepService.getAllTestStepsWithResultId(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/result_type/{resultTypeId}/subtests/{subTestId}")
    public ResponseEntity<GetTestStepsWithResultIdOfSubTestResponse> getAllTestStepsWithResultIdOfSubTestWithId(@PathVariable(name = "resultTypeId") Integer resultId, @PathVariable(name = "subTestId") Integer subTestId) {
        GetTestStepsWithResultIdOfSubTestRequest request = new GetTestStepsWithResultIdOfSubTestRequest(subTestId,  resultId);
        GetTestStepsWithResultIdOfSubTestResponse response = testStepService.getAllTestStepsWithResultIdOfSubTest(request);
        return ResponseEntity.ok(response);
    }


}