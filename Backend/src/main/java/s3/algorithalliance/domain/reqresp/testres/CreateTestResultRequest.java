package s3.algorithalliance.domain.reqresp.testres;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTestResultRequest {
    private String name;
    private Integer testSetId;
    private String duration;
}
