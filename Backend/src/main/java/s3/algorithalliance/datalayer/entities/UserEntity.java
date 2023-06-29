package s3.algorithalliance.datalayer.entities;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "al_users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Integer id;
    @Column(name = "email")
    @NotNull
    @Length(max = 50)
    private String email;
    @Column(name = "password")
    @NotNull
    @Length(max = 100)
    @EqualsAndHashCode.Exclude
    private String password;
    @Column(name = "username")
    @NotNull
    @Length(max = 50)
    private String username;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private UserRoleEntity userRole;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKeyColumn(name = "id")
    @NotNull
    private Map<Integer, BranchEntity> branches = new HashMap<>();
}
