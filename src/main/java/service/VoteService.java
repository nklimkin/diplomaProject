package service;

import model.Restaurant;
import model.Vote;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repository.RestaurantRepository;
import repository.VoteRepository;
import to.VoteTo;
import util.RatingUtil;
import util.VoteUtil;

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

    public Vote create(VoteTo voteTo, int userId, int restaurantId) {
        Vote vote = VoteUtil.createNewVoteFomToByUserIdAndRestaurantId(voteTo);
        checkNew(vote);
        restaurantRepository.updateRatingOfRestaurantByNewVote(restaurantId, vote.getGrade());
        return voteRepository.save(vote, userId, restaurantId);
    }

    public void update(VoteTo voteTo, int userId, int restaurantId) {
        Vote vote = VoteUtil.createNewVoteFomToByUserIdAndRestaurantId(voteTo);
        checkUpdated(vote);
//        checkTimeForUpdateVote(vote);
        voteRepository.save(vote, userId, restaurantId);

        int idOfRestaurantTo = voteTo.getRestaurantId();

        updateRating(idOfRestaurantTo, restaurantId);
    }


    private void updateRating(int idOfPastRestaurant, int idOfNewRestaurant) {
        if (idOfPastRestaurant == idOfNewRestaurant) {
            List<Vote> todayVotesOfCurrentRestaurant = getAllVoteByRestaurant(idOfPastRestaurant);
            double newRating = RatingUtil.countTotalRating(todayVotesOfCurrentRestaurant);
            restaurantRepository.updateRating(idOfPastRestaurant, newRating);
        }

        else {
            List<Vote> todayVotesOfPastRestaurant = getAllVoteByRestaurant(idOfPastRestaurant);
            double newRatingOfPastRestaurant = RatingUtil.countTotalRating(todayVotesOfPastRestaurant);
            restaurantRepository.updateRating(idOfPastRestaurant, newRatingOfPastRestaurant);

            List<Vote> todayVotesOfNewRestaurant = getAllVoteByRestaurant(idOfNewRestaurant);
            double newRatingOfNewRestaurant = RatingUtil.countTotalRating(todayVotesOfNewRestaurant);
            restaurantRepository.updateRating(idOfNewRestaurant, newRatingOfNewRestaurant);
        }
    }

    public void delete(int id) {
        Vote currentVote = checkNotFoundWithId(voteRepository.get(id), id);
        LocalDate currentLocalDate = currentVote.getLocalDate();
        Restaurant currentRestaurant = currentVote.getRestaurant();
        Integer idOfRestaurant = currentRestaurant.getId();
        Assert.notNull(idOfRestaurant, "id of restaurant must not be null");
        voteRepository.delete(id);

        List<Vote> todayVotesOfRestaurant = getAllVoteByRestaurant(idOfRestaurant);
        double newRating = RatingUtil.countTotalRating(todayVotesOfRestaurant);
        restaurantRepository.updateRating(idOfRestaurant, newRating);
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

    public Vote getTodayByUser(int userId) {
        return checkNotFountWithSomeAttribute(voteRepository.getTodayVoteByUserId(userId), userId);
    }
}
