package s3.algorithalliance.servicelayer;

import s3.algorithalliance.domain.AccessToken;

public interface IAccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
