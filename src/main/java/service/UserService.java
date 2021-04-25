package service;

import model.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.UserRepository;
import util.ValidationUtil;
import authorized.AuthorizedUser;

import java.util.List;

import static util.UserUtil.prepareToSave;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private final UserRepository rep;
    private final PasswordEncoder encoder;

    public UserService(UserRepository rep, PasswordEncoder encoder){
        this.rep = rep;
        this.encoder = encoder;
    }

    public User save(User user) {
        ValidationUtil.checkNew(user);
        return prepareAndSave(user);
    }

    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(rep.delete(id), id);
    }

    public void update(User user, int id) {
        ValidationUtil.checkUpdated(user);
        ValidationUtil.assureIdConsistent(user, id);
        prepareAndSave(user);
    }

    private User prepareAndSave(User user) {
        return rep.save(prepareToSave(user, encoder));
    }

    public User get(int id) {
        return ValidationUtil.checkNotFoundWithId(rep.get(id), id);
    }

    public User getUserByEmail(String email) {
        return ValidationUtil.checkNotFountWithSomeAttribute(rep.getByEmail(email), email);
    }

    public List<User> getAll() {
        return rep.getAll();
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = rep.getByEmail(email);
        if (user == null)
            throw new IllegalArgumentException("User with email " + email + " not found");
        return new AuthorizedUser(user);
    }
}
