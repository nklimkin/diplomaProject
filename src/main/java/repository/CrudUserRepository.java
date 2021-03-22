package repository;

import model.Status;
import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudUserRepository extends JpaRepository<User, Integer> {

    User getUserByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.status=:status WHERE u.id=:id")
    int delete(@Param("id") int id, @Param("status")Status status);

}
