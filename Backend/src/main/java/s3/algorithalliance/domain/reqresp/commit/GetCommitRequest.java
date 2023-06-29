package s3.algorithalliance.domain.reqresp.commit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCommitRequest {
    private Integer commitId;
}
