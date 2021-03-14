package repository;

import model.Restaurant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class JpaRestaurantRepTest {

    @Autowired
    private RestaurantRepository rep;

    @Test
    public void save() {
        rep.save(RestaurantTestData.getNew());
        assertThat(rep.get(RestaurantTestData.RESTAURANT_NEW))
                .isEqualToIgnoringGivenFields(RestaurantTestData.newRestaurant,"localDateTime", "menu");
    }

    @Test
    public void get() {
        assertThat(rep.get(RestaurantTestData.RESTAURANT_1))
                .isEqualToIgnoringGivenFields(RestaurantTestData.restaurant1
                        , "localDateTime", "menu");
    }

    @Test
    public void getBestOne() {
        assertThat(rep.getBestOne())
                .isEqualToIgnoringGivenFields(RestaurantTestData.restaurant3,
                        "localDateTime", "menu");
    }

    @Test
    public void delete() {
        rep.delete(RestaurantTestData.RESTAURANT_1);
        assertThat(rep.get(RestaurantTestData.RESTAURANT_1)).isEqualTo(null);
    }

    @Test
    public void getAll() {
        List<Restaurant> actualRestaurants = rep.getAll();
        assertThat(actualRestaurants).usingElementComparatorIgnoringFields("localDateTime", "menu")
                .containsExactlyInAnyOrder(RestaurantTestData.restaurant1,
                        RestaurantTestData.restaurant2, RestaurantTestData.restaurant3);
    }
}