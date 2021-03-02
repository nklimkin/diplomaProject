package repository;

import model.HistoryOfVote;
import org.springframework.stereotype.Repository;

@Repository
public class JpaHistoryOfVoteRep implements HistoryOfVoteRepository{

    @Override
    public HistoryOfVote save(HistoryOfVote historyOfVote) {
        return null;
    }

    @Override
    public HistoryOfVote delete(int id) {
        return null;
    }

    @Override
    public HistoryOfVote get(int id) {
        return null;
    }

    @Override
    public HistoryOfVote getAll() {
        return null;
    }
}
