package s3.algorithalliance.domain.reqresp.commit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import s3.algorithalliance.domain.Commit;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCommitsResponse {
    private Map<Integer, Commit> commits;
}
