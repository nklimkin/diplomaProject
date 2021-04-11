package service;

import model.Restaurant;
import model.Vote;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repository.RestaurantRepository;
import repository.VoteRepository;
import util.RatingUtil;
import util.ValidationUtil;

import java.time.LocalDate;
import java.util.List;

import static util.ValidationUtil.*;

@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;

    public VoteService(VoteRepository voteRepository, RestaurantRepository restaurantRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Vote create(Vote vote, int userId, int restaurantId) {
        checkNew(vote);
        restaurantRepository.updateRatingOfRestaurantNewVote(restaurantId, vote.getGrade());
        return voteRepository.save(vote, userId, restaurantId);
    }

    public void update(Vote vote, int userId, int idOfPastRestaurantForVote) {
        checkUpdated(vote);
//        checkTimeForUpdateVote(vote);
        checkPossibilityOfUpdateVote(vote, userId);
        Assert.notNull(vote.getRestaurant(), "restaurant must not be null");
        Assert.notNull(vote.getRestaurant().getId(), "id of restaurant must not be null");
        Restaurant newRestaurantForVote = vote.getRestaurant();
        voteRepository.save(vote, userId, newRestaurantForVote.getId());

        if (newRestaurantForVote.getId() == idOfPastRestaurantForVote) {
            restaurantRepository.updateRatingOfSameRestaurantByUpdatedVote(newRestaurantForVote.getId(),
                    RatingUtil.countTotalRating(getAllTodayVoteByRestaurant(newRestaurantForVote.getId())));
        } else {
            double newRatingOfLastRestaurant = RatingUtil.countTotalRating(getAllTodayVoteByRestaurant(idOfPastRestaurantForVote));
            double newRatingOfCurrentRestaurant = RatingUtil.countTotalRating(getAllTodayVoteByRestaurant(newRestaurantForVote.getId()));
            restaurantRepository.updateRatingOfTwoRestaurantsByUpdatedVote(idOfPastRestaurantForVote,
                    newRestaurantForVote.getId(), newRatingOfLastRestaurant, newRatingOfCurrentRestaurant);
        }
    }

    public void delete(int id) {
        Vote currentVote = checkNotFoundWithId(voteRepository.get(id), id);
        LocalDate currentLocalDate = currentVote.getLocalDate();
        Restaurant currentRestaurant = currentVote.getRestaurant();
        Assert.notNull(currentRestaurant, "restaurant must not be null");
        Integer idOfRestaurant = currentRestaurant.getId();
        Assert.notNull(idOfRestaurant, "id of restaurant must not be null");
        voteRepository.delete(id);
        List<Vote> allVoteInThatDay = voteRepository.getAllVoteByRestaurantAndLocalDate(idOfRestaurant, currentLocalDate);
        System.out.println(allVoteInThatDay);
        double newRating = RatingUtil.countTotalRating(allVoteInThatDay);
        restaurantRepository.updateRatingOfSameRestaurantByUpdatedVote(idOfRestaurant, newRating);
    }

    public List<Vote> getAll() {
        return voteRepository.getAll();
    }

    public List<Vote> getVotesWithUserAndRestaurant() {
        return voteRepository.getVotesWithUserAndRestaurant();
    }

    public Vote get(int id) {
        return checkNotFoundWithId(voteRepository.get(id), id);
    }

    public List<Vote> getAllVoteByRestaurant(int restaurantId) {
        return checkNotFountWithSomeAttribute(voteRepository.getAllVoteByRestaurant(restaurantId), restaurantId);
    }

    public List<Vote> getAllTodayVoteByRestaurant(int restaurantId) {
        return checkNotFountWithSomeAttribute(voteRepository.getAllTodayVoteByRestaurant(restaurantId), restaurantId);
    }

    public List<Vote> getAllVoteByRestaurantAndLocalDate(int restaurantId, LocalDate localDate) {
        return voteRepository.getAllVoteByRestaurantAndLocalDate(restaurantId, localDate);
    }
}
