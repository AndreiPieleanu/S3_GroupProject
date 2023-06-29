package s3.algorithalliance.domain.reqresp.testres;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import s3.algorithalliance.domain.Test;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTestResultsResponse {
    private Map<Integer, Test> testResults;
}
