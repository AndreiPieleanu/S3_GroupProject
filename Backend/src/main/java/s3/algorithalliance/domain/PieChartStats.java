package s3.algorithalliance.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class PieChartStats {
    private Integer passed;
    private Integer total;
}
