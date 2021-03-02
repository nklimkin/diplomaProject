package model;

import javax.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;
import java.util.Set;

@Entity
public class User extends BaseEntity{

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;
    private String surname;
    private String email;
    private String password;
    private Date registered = new Date();
    private Set<UserRoles> roles;

}
