package s3.algorithalliance.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class TestWarning {
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer id;
    private Integer warningCode;
}
