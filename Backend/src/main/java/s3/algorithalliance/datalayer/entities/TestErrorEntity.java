package s3.algorithalliance.datalayer.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "al_testerrors")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TestErrorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer id;
    @Column(name = "error_code")
    @NotNull
    private String errorCode;
}
