package s3.algorithalliance.domain.reqresp.teststep;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTestStepRequest {
    private Integer stepNo;
    private String resultDetails;
    private Integer subTestId;
    private Integer testErrorId;
    private Integer testWarningId;
    private Integer resultTypeId;
}
