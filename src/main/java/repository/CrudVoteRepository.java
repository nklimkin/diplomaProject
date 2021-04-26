package repository;

import model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote h WHERE h.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId")
    List<Vote> getAllByRestaurant(@Param("restaurantId") int restaurantId);

    Vote getVoteByUserIdAndLocalDate(int userId, LocalDate localDate);

    @EntityGraph(value = "voteWithUserAndRestaurant")
    @Query("SELECT v FROM Vote v")
    List<Vote> getVotesWithUserAndRestaurant();

}
