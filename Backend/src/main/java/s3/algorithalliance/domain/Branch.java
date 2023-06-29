package s3.algorithalliance.domain;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Branch {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer id;
    private String name;
    private User user;
    Map<Integer, Commit> commits;
}
