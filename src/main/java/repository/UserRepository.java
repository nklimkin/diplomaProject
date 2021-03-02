package repository;

import model.User;

public interface UserRepository {

    User save(User user);

    User get(int id);

    User getByEmail(String email);

    User getAll();

    User delete();
}
