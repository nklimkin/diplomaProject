package testData;

import model.Dish;
import model.Restaurant;
import model.Status;

import java.time.LocalDate;
import java.util.List;

public class RestaurantTestData {

    public static final int RESTAURANT_1 = 100003;
    public static final int RESTAURANT_2 = 100004;
    public static final int RESTAURANT_3 = 100005;
    public static final int RESTAURANT_NEW = 100014;

    public static Restaurant restaurant1 = new Restaurant(100003,"restaurant1",
            List.of(DishesTestData.dish1, DishesTestData.dish2, DishesTestData.dish3),
            4.0, 10, LocalDate.now(), Status.ACTIVE);

    public static Restaurant restaurant2 = new Restaurant(100004,"restaurant2",
            List.of(DishesTestData.dish4),  3.0, 1, LocalDate.now(), Status.ACTIVE);

    public static Restaurant restaurant3 = new Restaurant(100005,"restaurant3",
            List.of(DishesTestData.dish5, DishesTestData.dish6), 4.1, 3, LocalDate.now(), Status.ACTIVE);

    public static Restaurant newRestaurant = new Restaurant(100014, "newRestaurant",
            List.of(DishesTestData.newDish1, DishesTestData.newDish2), 0, 0, LocalDate.now(), Status.ACTIVE);

    public static Restaurant updatedRestaurant = new Restaurant(RESTAURANT_1, "updatedRes",
            List.of(DishesTestData.dish1, DishesTestData.dish2, DishesTestData.dish3, DishesTestData.newDish3),
            2.0, 4, LocalDate.now(), Status.ACTIVE);

    public static List<Restaurant> restaurants = List.of(restaurant2, restaurant1, restaurant3);

    public static Restaurant getNew() {
        return new Restaurant(null, "newRestaurant", List.of(DishesTestData.newDish1, DishesTestData.newDish2),
                0, 0, LocalDate.now(), Status.ACTIVE);
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(restaurant1);
        updated.setLabel("updatedRes");
        updated.setRating(2.0);
        updated.setCountOfVoters(4);
        updated.setMenu(List.of(DishesTestData.dish1, DishesTestData.dish2, DishesTestData.dish3, DishesTestData.newDish3));
        return updated;
    }


    public static class DishesTestData {

        public static Dish dish1 = new Dish(100008, "dish11", 500, restaurant1);
        public static Dish dish2 = new Dish(100009, "dish21", 100.43, restaurant1);
        public static Dish dish3 = new Dish(100010, "dish31", 100, restaurant1);
        public static Dish dish4 = new Dish(100011, "dish12", 200, restaurant2);
        public static Dish dish5 = new Dish(100012, "dish13", 10, restaurant3);
        public static Dish dish6 = new Dish(100013, "dish23", 160, restaurant3);

        public static Dish newDish1 = new Dish(100015, "newDish1", 5, newRestaurant);
        public static Dish newDish2 = new Dish(100016, "newDish2", 15, newRestaurant);

        public static Dish newDish3 = new Dish(null, "newDish3", 1);

    }
}
