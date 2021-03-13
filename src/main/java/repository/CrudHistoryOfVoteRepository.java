package repository;

import model.HistoryOfVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudHistoryOfVoteRepository extends JpaRepository<HistoryOfVote, Integer> {

    HistoryOfVote getHistoryOfVoteByUserId(int userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM HistoryOfVote h WHERE h.id=:id")
    int delete(@Param("id") int id);
}
