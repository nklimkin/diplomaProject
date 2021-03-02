package repository;

import model.User;

import java.util.List;

public interface UserRepository {

    User save(User user);

    User get(int id);

    User getByEmail(String email);

    List<User> getAll();

    boolean delete(int id);
}
