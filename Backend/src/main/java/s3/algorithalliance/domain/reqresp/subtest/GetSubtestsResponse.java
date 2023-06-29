package s3.algorithalliance.domain.reqresp.subtest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import s3.algorithalliance.domain.SubTest;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSubtestsResponse {
    private Map<Integer, SubTest> subtests;
}
