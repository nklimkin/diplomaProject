package repository;

import model.User;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaUserRep implements UserRepository{

    @PersistenceContext
    private EntityManager em;
    @Override
    public User save(User user) {
        if (user.isNew()) {
            em.persist(user);
            return user;
        } else {
            return em.merge(user);
        }
    }

    @Override
    public User get(int id) {
        return em.find(User.class, id);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = em.createNamedQuery(User.GET_BY_EMAIL, User.class)
                .setParameter("userEmail", email)
                .getResultList();
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return em.createNamedQuery(User.GET_ALL, User.class)
                .getResultList();
    }

    @Override
    public boolean delete(int id) {
        return em.createNamedQuery(User.DELETE, User.class)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }
}
