package s3.algorithalliance.domain.reqresp.branches;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBranchRequest {
    private String name;
    private Integer userId;
}
