package repository;

import model.Dish;
import model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    Restaurant get(int id);

    Restaurant getBestOne();

    boolean delete(int id);

    List<Restaurant> getAll();

    Restaurant getWithDishes(int id);

    void updateRatingOfRestaurantByNewVote(int id, int grade);

    void updateRating(int restaurantId, double countTotalRating);

}
