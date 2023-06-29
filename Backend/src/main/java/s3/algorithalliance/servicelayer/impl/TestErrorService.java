package s3.algorithalliance.servicelayer.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import s3.algorithalliance.datalayer.ITestErrorDal;
import s3.algorithalliance.datalayer.entities.TestErrorEntity;
import s3.algorithalliance.domain.TestError;
import s3.algorithalliance.domain.TestStep;
import s3.algorithalliance.domain.converters.ErrorConverter;
import s3.algorithalliance.domain.converters.TestStepConverter;
import s3.algorithalliance.domain.reqresp.testerror.GetTestErrorsRequest;
import s3.algorithalliance.domain.reqresp.testerror.GetTestErrorsResponse;
import s3.algorithalliance.domain.reqresp.teststep.GetTestStepsByErrorResponse;
import s3.algorithalliance.servicelayer.ITestErrorService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@AllArgsConstructor
public class TestErrorService implements ITestErrorService {
    private ITestErrorDal errorDal;

    @Override
    public GetTestErrorsResponse getTestErrors(){
        Map<Integer, TestError> testErrors = new HashMap<>();
        errorDal
                .findAll()
                .stream()
                .map(ErrorConverter::convert)
                .forEach(te -> testErrors.put(te.getId(), te));
        return new GetTestErrorsResponse(testErrors);
    }
}