package to;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;

public class UserTo extends BaseTo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String name;

    @NotBlank
    private String surName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 5, max = 32)
    private String password;

    public UserTo() {

    }

    public UserTo(Integer id, String name, String surName, String email, String password){
        super(id);
        this.name = name;
        this.surName = surName;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getSurName() {
        return surName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
