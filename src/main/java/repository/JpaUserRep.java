package repository;

import model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class JpaUserRep implements UserRepository{
    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User get(int id) {
        return null;
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public User getAll() {
        return null;
    }

    @Override
    public User delete() {
        return null;
    }
}
