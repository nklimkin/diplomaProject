package service;

import model.User;
import org.springframework.stereotype.Service;
import repository.UserRepository;
import util.ValidationUtil;

import java.util.List;

@Service
public class UserService {

    private final UserRepository rep;

    public UserService(UserRepository rep){
        this.rep = rep;
    }

    public User save(User user) {
        ValidationUtil.checkNew(user);
        return rep.save(user);
    }

    public void delete(int id) {
        ValidationUtil.checkNotFoundWithId(rep.delete(id), id);
    }

    public void update(User user) {
        ValidationUtil.checkUpdated(user);
        rep.save(user);
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

}
