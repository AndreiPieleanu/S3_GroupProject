package s3.algorithalliance.domain;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class User {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer id;
    private String email;
    private String username;
    private Map<Integer, Branch> branches;
}
