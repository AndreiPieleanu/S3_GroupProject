package s3.algorithalliance.domain.reqresp.commit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import s3.algorithalliance.domain.Commit;

import java.util.TreeMap;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetResultsForAllCommitsOfBranchWithIdResponse {
    private TreeMap<Commit, Integer> results;
}
