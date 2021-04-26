package util;

import model.Status;
import model.User;
import model.UserRoles;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import to.UserTo;

public class UserUtil {

    public static User prepareToSave(User user, PasswordEncoder encoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.hasText(password) ? encoder.encode(password) : password);
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

    public static UserTo convertUserToUserTo(User user){
        return new UserTo(user.getId(), user.getName(), user.getSurname(), user.getEmail(), user.getPassword());
    }

    public static User createNewFromTO(UserTo userTo) {
        return new User(null, userTo.getName(), userTo.getSurName(), userTo.getEmail(),
                userTo.getPassword(),
                Status.ACTIVE,
                UserRoles.USER);
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setSurname(userTo.getSurName());
        user.setEmail(userTo.getEmail());
        user.setPassword(userTo.getPassword());
        return user;
    }

}
