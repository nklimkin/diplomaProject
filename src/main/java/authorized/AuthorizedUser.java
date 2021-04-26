package authorized;

import model.User;
import to.UserTo;
import util.UserUtil;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User{

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = UserUtil.convertUserToUserTo(user);
    }

    public Integer getId() {
        return userTo.getId();
    }


}
