package s3.algorithalliance.domain;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class SubTest {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer id;
    private String name;
    private Test test;
    private Map<Integer, TestStep> testSteps;
}
