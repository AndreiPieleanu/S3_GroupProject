package s3.algorithalliance.domain;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class TestSet {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer id;
    private String name;
    private Integer branchId;
    private Commit commit;
    private Map<Integer, Test> tests;
}
