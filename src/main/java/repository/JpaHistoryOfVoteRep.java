package repository;

import model.Vote;
import model.Restaurant;
import model.User;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaHistoryOfVoteRep implements HistoryOfVoteRepository{

    private CrudHistoryOfVoteRepository crudHistoryOfVoteRepository;
    private CrudUserRepository crudUserRepository;
    private CrudRestaurantRepository crudRestaurantRepository;

    private final Sort SORT_BY_DATE = Sort.by(Sort.Direction.ASC, "localDateTime");

    public JpaHistoryOfVoteRep(CrudHistoryOfVoteRepository crudHistoryOfVoteRepository, CrudUserRepository crudUserRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudHistoryOfVoteRepository = crudHistoryOfVoteRepository;
        this.crudUserRepository = crudUserRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public Vote save(Vote vote, int restaurantId, int userId) {
        Restaurant restaurant = crudRestaurantRepository.getOne(restaurantId);
        User user = crudUserRepository.getOne(userId);
        vote.setUser(user);
        vote.setRestaurant(restaurant);
        return crudHistoryOfVoteRepository.save(vote);
    }

    @Override
    public boolean delete(int id) {
        return crudHistoryOfVoteRepository.delete(id) != 0;
    }

    @Override
    public Vote get(int id) {
        return crudHistoryOfVoteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Vote> getAll() {
        return crudHistoryOfVoteRepository.findAll(SORT_BY_DATE);
    }

    @Override
    public Vote getHistoryOfVoteByUserId(int userId) {
        return crudHistoryOfVoteRepository.getHistoryOfVoteByUserId(userId);
    }
}
