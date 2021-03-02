package repository;

import model.Restaurant;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class JpaRestaurantRep implements RestaurantRepository{
    @Override
    public Restaurant save(Restaurant restaurant) {
        return null;
    }

    @Override
    public Restaurant get(int id) {
        return null;
    }

    @Override
    public Restaurant getBestOne() {
        return null;
    }

    @Override
    public Restaurant delete(int id) {
        return null;
    }

    @Override
    public Restaurant getAll() {
        return null;
    }
}
