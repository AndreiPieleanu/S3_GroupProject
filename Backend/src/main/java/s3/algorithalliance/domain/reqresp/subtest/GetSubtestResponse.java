package s3.algorithalliance.domain.reqresp.subtest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import s3.algorithalliance.domain.SubTest;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSubtestResponse {
    private SubTest subTest;
}
