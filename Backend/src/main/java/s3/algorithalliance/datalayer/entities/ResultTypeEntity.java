package s3.algorithalliance.datalayer.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "al_resulttype")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResultTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer id;
    @Column(name = "result_name")
    @Length(max = 50)
    @NotNull
    private String resultName;
}
