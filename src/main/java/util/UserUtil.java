package util;

import model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

public class UserUtil {

    public static User prepareToSave(User user, PasswordEncoder encoder) {
        String password = user.getPassword();
        user.setPassword(StringUtils.hasText(password) ? encoder.encode(password) : password);
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
