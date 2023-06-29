package s3.algorithalliance.datalayer.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Entity
@Table(name = "al_branches")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BranchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer id;
    @Column(name = "branch_name")
    @NotNull
    @Length(max = 50)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;
    @OneToMany(mappedBy = "originBranch", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyColumn(name = "id")
    @NotNull
    private Map<Integer, CommitEntity> commits;
}
