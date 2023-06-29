package s3.algorithalliance.datalayer.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

@Entity
@Table(name = "al_commits")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CommitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer id;
    @Column(name = "version")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private String version;
    @Column(name = "version_date")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private Timestamp versionDate = Timestamp.from(Instant.now());
    @Column(name = "name")
    @NotNull
    @Length(max = 50)
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", referencedColumnName = "id")
    private BranchEntity originBranch;
    @OneToMany(mappedBy = "commitEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyColumn(name = "id")
    @NotNull
    private Map<Integer, TestSetEntity> testSets;
}
