package s3.algorithalliance.domain.reqresp.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import s3.algorithalliance.domain.User;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {
    User foundUser;
}
