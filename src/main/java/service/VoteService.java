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

    public Vote create(Vote vote, int userId) {
        checkNew(vote);
        Assert.notNull(vote.getRestaurant().getId(), "id of restaurant of vote must not be null");
        int restaurantId = vote.getRestaurant().getId();
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
        int idOfNewRestaurantForVote = newRestaurantForVote.getId();
        voteRepository.save(vote, userId, newRestaurantForVote.getId());

        if (idOfNewRestaurantForVote == idOfPastRestaurantForVote) {
            double newRatingOfRestaurant = RatingUtil.countTotalRating(getAllTodayVoteByRestaurant(idOfNewRestaurantForVote));
            restaurantRepository.updateRatingOfSameRestaurantByUpdatedVote(
                    newRestaurantForVote.getId(),
                    newRatingOfRestaurant);
        } else {
            double newRatingOfLastRestaurant = RatingUtil.countTotalRating(getAllTodayVoteByRestaurant(idOfPastRestaurantForVote));
            double newRatingOfNewRestaurant = RatingUtil.countTotalRating(getAllTodayVoteByRestaurant(idOfNewRestaurantForVote));
            restaurantRepository.updateRatingOfTwoRestaurantsByUpdatedVote(idOfPastRestaurantForVote,
                    idOfNewRestaurantForVote, newRatingOfLastRestaurant, newRatingOfNewRestaurant);
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

    public Vote getTodayByUser(int userId) {
        return checkNotFountWithSomeAttribute(voteRepository.getTodayVoteByUserId(userId), userId);
    }
}
