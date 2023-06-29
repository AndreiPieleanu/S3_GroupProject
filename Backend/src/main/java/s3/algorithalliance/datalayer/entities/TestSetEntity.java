package s3.algorithalliance.datalayer.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Entity
@Table(name = "al_testsets")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TestSetEntity {
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
    @JoinColumn(name = "commit_id", referencedColumnName = "id")
    private CommitEntity commitEntity;
    @OneToMany(mappedBy = "testSet", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyColumn(name = "id")
    @NotNull
    private Map<Integer, TestEntity> testResults;
}
