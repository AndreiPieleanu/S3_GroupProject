package s3.algorithalliance.datalayer.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "al_testwarnings")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TestWarningEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer id;
    @Column(name = "warning_code")
    @NotNull
    private Integer warningCode;
}
