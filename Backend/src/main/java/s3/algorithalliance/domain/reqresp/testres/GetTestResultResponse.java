package s3.algorithalliance.domain.reqresp.testres;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import s3.algorithalliance.domain.Test;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTestResultResponse {
    private Test test;
}
