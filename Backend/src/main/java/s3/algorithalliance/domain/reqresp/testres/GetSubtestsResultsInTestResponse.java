package s3.algorithalliance.domain.reqresp.testres;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSubtestsResultsInTestResponse {
    private Map<Integer, Integer> response;
}
