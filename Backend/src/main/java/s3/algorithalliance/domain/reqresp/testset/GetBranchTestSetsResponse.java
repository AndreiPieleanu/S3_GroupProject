package s3.algorithalliance.domain.reqresp.testset;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import s3.algorithalliance.domain.TestSet;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBranchTestSetsResponse {
    private Map<Integer, TestSet> testSets;
}