package repository;

import model.Vote;
import model.Restaurant;
import model.User;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class JpaVoteRep implements VoteRepository {

    private CrudVoteRepository crudVoteRepository;
    private CrudUserRepository crudUserRepository;
    private CrudRestaurantRepository crudRestaurantRepository;

    private final Sort SORT_BY_DATE = Sort.by(Sort.Direction.ASC, "localDate");

    public JpaVoteRep(CrudVoteRepository crudVoteRepository, CrudUserRepository crudUserRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudVoteRepository = crudVoteRepository;
        this.crudUserRepository = crudUserRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public Vote save(Vote vote, int userId, int restaurantId) {
        User user = crudUserRepository.getOne(userId);
        Restaurant restaurant  = crudRestaurantRepository.getOne(restaurantId);
        vote.setUser(user);
        vote.setRestaurant(restaurant);
        return crudVoteRepository.save(vote);
    }

    @Override
    public boolean delete(int id) {
        return crudVoteRepository.delete(id) != 0;
    }

    @Override
    public Vote get(int id) {
        return crudVoteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Vote> getAll() {
        return crudVoteRepository.findAll(SORT_BY_DATE);
    }

    @Override
    public List<Vote> getVotesWithUserAndRestaurant() {
        return crudVoteRepository.getVotesWithUserAndRestaurant();
    }

    @Override
    public List<Vote> getAllVoteByRestaurant(int restaurantId) {
        return crudVoteRepository.getAllByRestaurant(restaurantId);
    }

    @Override
    public List<Vote> getAllTodayVoteByRestaurant(int restaurantId) {
        return crudVoteRepository.getAllVoteByRestaurantAndLocalDate(restaurantId, LocalDate.now());
    }

    @Override
    public List<Vote> getAllVoteByRestaurantAndLocalDate(int restaurantId, LocalDate localDate) {
        return crudVoteRepository.getAllVoteByRestaurantAndLocalDate(restaurantId, localDate);
    }

    @Override
    public Vote getTodayVoteByUserId(int userId) {
        return crudVoteRepository.getVoteByUserIdAndLocalDate(userId, LocalDate.now());
    }

}
