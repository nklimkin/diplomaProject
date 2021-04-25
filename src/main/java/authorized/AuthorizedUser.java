package authorized;

import model.User;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User{

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
    }


}
