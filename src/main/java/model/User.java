package model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = User.GET_BY_EMAIL, query = "SELECT u FROM User u WHERE u.email=:userEmail"),
        @NamedQuery(name = User.GET_ALL, query = "SELECT u FROM User u ORDER BY u.surname, u.email"),
        @NamedQuery(name = User.DELETE, query = "DELETE FROM User u WHERE u.id=:id")
})
public class User extends BaseEntity{

    public static final String GET_BY_EMAIL = "user.getByEmail";
    public static final String GET_ALL = "user.getAll";
    public static final String DELETE = "user.delete";

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @Column(name = "surname", nullable = false)
    @NotBlank
    private String surname;

    @Column(name = "email", nullable = false)
    @NotBlank
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 6)
    private String password;

    @Column(name = "registered", nullable = false)
    @NotNull
    private Date registered = new Date();

    @Enumerated
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UserRoles> roles;
}
