package s3.algorithalliance.domain.reqresp.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    private Integer id;
    private String username;
    private String email;
    private String password; 
}
