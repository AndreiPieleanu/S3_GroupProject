package s3.algorithalliance.domain.reqresp.testset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import s3.algorithalliance.domain.TestSet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTestSetResponse {
    private TestSet testSet;
}
