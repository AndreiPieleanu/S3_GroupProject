package s3.algorithalliance.domain.reqresp.testset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTestResultsInTestSetResponse {
    private Map<Integer, Integer> response;
}
