package s3.algorithalliance.domain.reqresp.testerror;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTestErrorsRequest {
    private Integer errorId;

    private String errorCode;
}
