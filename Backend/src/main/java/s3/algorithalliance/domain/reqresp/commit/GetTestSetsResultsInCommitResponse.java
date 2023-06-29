package s3.algorithalliance.domain.reqresp.commit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTestSetsResultsInCommitResponse {
    private Map<Integer, Integer> response;
}
