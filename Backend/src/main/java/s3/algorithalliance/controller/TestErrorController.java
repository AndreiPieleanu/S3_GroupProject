package s3.algorithalliance.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s3.algorithalliance.domain.reqresp.branches.GetPublicBranchesResponse;
import s3.algorithalliance.domain.reqresp.testerror.GetTestErrorsResponse;
import s3.algorithalliance.servicelayer.ITestErrorService;

@RestController
@RequestMapping("/testErrors")
@AllArgsConstructor
public class TestErrorController {
    private ITestErrorService testErrorService;

    @GetMapping("/{testErrors}")
    public ResponseEntity<GetTestErrorsResponse> getTestErrors(){
            GetTestErrorsResponse response = testErrorService.getTestErrors();
            return ResponseEntity.ok(response);
    }
}