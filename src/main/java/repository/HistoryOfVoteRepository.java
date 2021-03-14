package repository;

import model.Vote;

import java.util.List;

public interface HistoryOfVoteRepository {

    Vote save(Vote vote, int restaurantId, int userId);

    boolean delete(int id);

    Vote get(int id);

    List<Vote> getAll();

    Vote getHistoryOfVoteByUserId(int userId);
}
