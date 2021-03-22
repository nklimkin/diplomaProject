package service;

import model.Restaurant;
import model.Vote;
import org.springframework.stereotype.Service;
import repository.JpaRestaurantRep;
import repository.JpaUserRep;
import repository.JpaVoteRep;
import util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class RestaurantService {

    private final JpaRestaurantRep jpaRestaurantRep;

    public RestaurantService(JpaRestaurantRep jpaRestaurantRep, JpaVoteRep jpaVoteRep, JpaUserRep jpaUserRep) {
        this.jpaRestaurantRep = jpaRestaurantRep;
    }

    public Restaurant save(Restaurant restaurant){
        ValidationUtil.checkNew(restaurant);
        return jpaRestaurantRep.save(restaurant);
    }

    public void update(Restaurant restaurant){
        ValidationUtil.checkUpdated(restaurant);
        jpaRestaurantRep.save(restaurant);
    }

    public Restaurant get(int id){
        return ValidationUtil.checkNotFoundWithId(jpaRestaurantRep.get(id), id);
    }

    public Restaurant getTopRestaurant(){
        return ValidationUtil.checkNotFound(jpaRestaurantRep.getBestOne());
    }

    public List<Restaurant> getAll() {
        return jpaRestaurantRep.getAll();
    }

    public Restaurant getWithMenu(int id) {
        return jpaRestaurantRep.getWithDishes(id);
    }

}
