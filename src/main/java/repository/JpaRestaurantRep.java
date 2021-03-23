package repository;

import model.Dish;
import model.Restaurant;
import model.Status;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import util.RatingUtil;

import java.util.Comparator;
import java.util.List;

@Repository
public class JpaRestaurantRep implements RestaurantRepository{

    private final Sort SORT_BY_RATING = Sort.by(Sort.Direction.ASC, "rating");

    private final CrudRestaurantRepository crudRestaurantRepository;

    public JpaRestaurantRep(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return crudRestaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant get(int id) {
        return crudRestaurantRepository.findById(id).orElse(null);
    }

    @Override
    public Restaurant getBestOne() {
        return getAll().stream().max(Comparator.comparingDouble(Restaurant::getRating)).orElse(null);
    }

    @Override
    public boolean delete(int id) {
        return crudRestaurantRepository.delete(id, Status.DELETE) != 0;
    }

    @Override
    public List<Restaurant> getAll() {
        List<Restaurant> restaurants = crudRestaurantRepository.findAll(SORT_BY_RATING);
        restaurants.forEach(rest -> rest.getMenu());
        System.out.println(restaurants);
        return restaurants;
    }

    @Override
    public Restaurant getWithDishes(int id) {
        return crudRestaurantRepository.getRestaurantById(id);
    }

    @Transactional
    @Override
    public void updateRatingOfRestaurantNewVote(int id, int grade) {
        Restaurant currentRestaurant = get(id);
        currentRestaurant.setRating(RatingUtil.countNewRating(
                currentRestaurant.getRating(), currentRestaurant.getCountOfVoters(), grade));
        currentRestaurant.setCountOfVoters(currentRestaurant.getCountOfVoters() + 1);
        save(currentRestaurant);
    }

    @Transactional
    @Override
    public void updateRatingOfSameRestaurantByUpdatedVote(int restaurantId, double countTotalRating) {
        Restaurant currentRestaurant = get(restaurantId);
        currentRestaurant.setRating(countTotalRating);
        currentRestaurant.setCountOfVoters(currentRestaurant.getCountOfVoters() + 1);
        save(currentRestaurant);
    }

    @Transactional
    @Override
    public void updateRatingOfTwoRestaurantsByUpdatedVote(int idOfPastRestaurant, int idOfCurrentRestaurant, double newRatingOfPastRestaurant, double newRatingOfCurrentRestaurant) {
        updateRatingOfSameRestaurantByUpdatedVote(idOfPastRestaurant, newRatingOfPastRestaurant);
        updateRatingOfSameRestaurantByUpdatedVote(idOfCurrentRestaurant, newRatingOfCurrentRestaurant);
    }

}
