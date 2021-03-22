package repository;

import model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    Vote getHistoryOfVoteByUserId(int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote h WHERE h.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId")
    List<Vote> getAllByRestaurant(@Param("restaurantId") int restaurantId);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId AND v.localDate=:localDate")
    List<Vote> getAllVoteByRestaurantAndLocalDate(@Param("restaurantId") int restaurantId,
                                                  @Param("localDate") LocalDate localDate);

    Vote getVoteByUserIdAndLocalDate(int userId, LocalDate localDate);

}
