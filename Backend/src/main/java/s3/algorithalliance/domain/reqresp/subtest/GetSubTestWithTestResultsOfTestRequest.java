package s3.algorithalliance.domain.reqresp.subtest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSubTestWithTestResultsOfTestRequest {
    private Integer testId;
    private Integer resultId;
}
