package s3.algorithalliance.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class CommitError {
    private Branch branch;
    private Commit errorStartCommit;
    private Commit lastCommit;
}
