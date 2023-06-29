package s3.algorithalliance.datalayer.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "al_user_roles")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @NotNull
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;
}
