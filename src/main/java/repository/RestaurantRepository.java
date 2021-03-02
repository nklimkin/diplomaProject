package repository;

import model.Restaurant;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    Restaurant get(int id);

    Restaurant getBestOne();

    Restaurant delete(int id);

    Restaurant getAll();

}
