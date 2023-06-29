package s3.algorithalliance.domain;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class TestStep {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer id;
    private Integer stepNo;
    private String result_details;
    private SubTest subTest;
    private TestError testError;
    private TestWarning testWarning;
    private ResultType resultType;
}
