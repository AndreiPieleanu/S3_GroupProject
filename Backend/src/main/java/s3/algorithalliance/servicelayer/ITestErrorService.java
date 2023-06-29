package s3.algorithalliance.servicelayer;

import org.springframework.stereotype.Service;
import s3.algorithalliance.domain.reqresp.testerror.GetTestErrorsRequest;
import s3.algorithalliance.domain.reqresp.testerror.GetTestErrorsResponse;
@Service
public interface ITestErrorService {
    GetTestErrorsResponse getTestErrors();
}
