package s3.algorithalliance.domain.reqresp.testerror;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import s3.algorithalliance.domain.TestError;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTestErrorsResponse {
    private Map<Integer, TestError> testErrors;
}
