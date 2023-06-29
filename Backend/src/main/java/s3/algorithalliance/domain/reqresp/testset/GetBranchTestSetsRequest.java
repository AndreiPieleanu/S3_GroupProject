package s3.algorithalliance.domain.reqresp.testset;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBranchTestSetsRequest {
    private Integer branchId;
}