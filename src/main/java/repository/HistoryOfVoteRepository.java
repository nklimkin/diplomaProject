package repository;

import model.HistoryOfVote;

public interface HistoryOfVoteRepository {

    HistoryOfVote save(HistoryOfVote historyOfVote);

    HistoryOfVote delete(int id);

    HistoryOfVote get(int id);

    HistoryOfVote getAll();
}
