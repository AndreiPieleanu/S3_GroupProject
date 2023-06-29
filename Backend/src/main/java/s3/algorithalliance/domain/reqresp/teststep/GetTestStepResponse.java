package s3.algorithalliance.domain.reqresp.teststep;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import s3.algorithalliance.domain.TestStep;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTestStepResponse {
    private TestStep testStep;
}
