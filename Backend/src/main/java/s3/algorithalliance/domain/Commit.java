package s3.algorithalliance.domain;

import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Commit implements Comparable<Commit>{
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer id;
    private String version;
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Timestamp versionDate = Timestamp.from(Instant.now());
    private String name;
    private Branch originBranch;
    private Map<Integer, TestSet> testSets;

    @Override
    public int compareTo(Commit o) {
        return this.versionDate.compareTo(o.versionDate);
    }
}
