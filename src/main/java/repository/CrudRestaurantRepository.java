package repository;

import model.Restaurant;
import model.Status;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE Restaurant r SET r.status=:status WHERE r.id=:id")
    int delete(@Param("id") int id, @Param("status") Status status);

    @Query("SELECT r FROM Restaurant r ORDER BY r.rating DESC")
    Restaurant getAllOrderByRating();

    List<Restaurant> getAllByDate(LocalDate localDate);

    @EntityGraph(attributePaths = {"menu"})
    Restaurant getRestaurantById(int id);

}
