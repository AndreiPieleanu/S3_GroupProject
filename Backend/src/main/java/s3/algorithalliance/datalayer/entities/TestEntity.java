package s3.algorithalliance.datalayer.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Map;

@Entity
@Table(name = "al_tests")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer id;
    @Column(name = "name")
    @NotNull
    @Length(max = 50)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_set_id", referencedColumnName = "id")
    private TestSetEntity testSet;
    @Column(name = "test_duration")
    @NotNull
    private String duration;
    @OneToMany(mappedBy = "testResult", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyColumn(name = "id")
    @NotNull
    private Map<Integer, SubtestEntity> subtests;
}
