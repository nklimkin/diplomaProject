package repository;

import model.HistoryOfVote;

import java.util.List;

public interface HistoryOfVoteRepository {

    HistoryOfVote save(HistoryOfVote historyOfVote, int restaurantId, int userId);

    boolean delete(int id);

    HistoryOfVote get(int id);

    List<HistoryOfVote> getAll();

    HistoryOfVote getHistoryOfVoteByUserId(int userId);
}
