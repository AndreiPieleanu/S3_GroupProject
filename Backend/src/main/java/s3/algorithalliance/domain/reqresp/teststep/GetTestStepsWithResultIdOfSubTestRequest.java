package s3.algorithalliance.domain.reqresp.teststep;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTestStepsWithResultIdOfSubTestRequest {
    private Integer subtestId;
    private Integer resultId;

}
