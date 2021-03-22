package repository;

import model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {

    Vote save(Vote vote, int userId, int restaurantId);

    boolean delete(int id);

    Vote get(int id);

    List<Vote> getAll();

    List<Vote> getAllVoteByRestaurant(int restaurantId);

    List<Vote> getAllTodayVoteByRestaurant(int restaurantId);

    List<Vote> getAllVoteByRestaurantAndLocalDate(int restaurantId, LocalDate localDate);

    Vote getTodayVoteByUserId(int userId);
}
