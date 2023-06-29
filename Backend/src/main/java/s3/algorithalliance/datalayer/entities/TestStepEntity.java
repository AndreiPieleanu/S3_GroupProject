package s3.algorithalliance.datalayer.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "al_teststeps")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TestStepEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer id;
    @Column(name = "step_no")
    @NotNull
    private Integer stepNo;
    @Column(name = "result_details")
    @NotNull
    private String result_details;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subtest_id", referencedColumnName = "id")
    private SubtestEntity subtest;
    @ManyToOne
    @JoinColumn(name = "error_id", referencedColumnName = "id")
    private TestErrorEntity testError;
    @ManyToOne
    @JoinColumn(name = "warning_id", referencedColumnName = "id")
    private TestWarningEntity testWarning;
    @ManyToOne
    @JoinColumn(name = "result_type_id", referencedColumnName = "id")
    private ResultTypeEntity resultType;
}