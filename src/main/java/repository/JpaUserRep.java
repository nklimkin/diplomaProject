package repository;

import model.Status;
import model.User;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaUserRep implements UserRepository{

    private final Sort SORT_BY_NAME = Sort.by(Sort.Direction.ASC, "name", "email");

    CrudUserRepository crudUserRepository;

    public JpaUserRep(CrudUserRepository crudUserRepository) {
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public User save(User user) {
        return crudUserRepository.save(user);
    }

    @Override
    public User get(int id) {
        return crudUserRepository.findById(id).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return crudUserRepository.getUserByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return crudUserRepository.findAll(SORT_BY_NAME);
    }

    @Override
    public boolean delete(int id) {
        return crudUserRepository.delete(id, Status.DELETE) != 0;
    }
}
