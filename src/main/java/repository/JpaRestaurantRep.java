package repository;

import model.Dish;
import model.Restaurant;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
        return crudRestaurantRepository.delete(id) != 0;
    }

    @Override
    public List<Restaurant> getAll() {
        return crudRestaurantRepository.findAll(SORT_BY_RATING);
    }

}
