package s3.algorithalliance.domain;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Test {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer id;
    private String name;
    private TestSet testSet;
    private String duration;
    private Map<Integer, SubTest> subtests;
}
