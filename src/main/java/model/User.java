package model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.Collection;
import java.util.EnumSet;
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

    @Column(name = "registered", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @NotNull
    private Date registered;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles")})
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UserRoles> roles;

    public User() {
    }

    public User(Integer id, @NotBlank String name, @NotBlank String surname, @NotBlank String email,
                @NotBlank @Size(min = 6) String password, Date registered, Set<UserRoles> roles) {

        super(id);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.registered = registered;
        this.roles = roles;
    }

    public User(Integer id, @NotBlank String name, @NotBlank String surname, @NotBlank String email,
                @NotBlank @Size(min = 6) String password, UserRoles role, UserRoles... roles) {
        super(id);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.roles = EnumSet.of(role, roles);
        this.registered = new Date(System.currentTimeMillis());
    }

    public User(User u){
        this(u.getId(), u.getName(), u.getSurname(), u.getEmail(), u.getPassword(), u.getRegistered()
        , u.roles);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + super.getId() +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registered=" + registered +
                ", roles=" + roles +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public Set<UserRoles> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRoles> roles) {
        this.roles = roles;
    }
}
