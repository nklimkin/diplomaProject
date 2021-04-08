package service;

import model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import testData.RestaurantTestData;
import testData.UserTestData;
import testData.VoteTestData;

import static testData.RestaurantTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles("h2")
public class RestaurantServiceTest {

    @Autowired
    private RestaurantService service;

    private final String[] fieldsToIgnore = new String[]{"menu", "date"};

    @Test
    public void save() {
        service.save(RestaurantTestData.getNew());
        assertThat(service.get(RESTAURANT_NEW)).usingRecursiveComparison()
                .ignoringFields(fieldsToIgnore)
                .isEqualTo(newRestaurant);
    }

    @Test
    public void update() {
        service.update(RestaurantTestData.getUpdated());
        assertThat(service.get(RESTAURANT_1)).usingRecursiveComparison()
                .ignoringFields(fieldsToIgnore)
                .isEqualTo(updatedRestaurant);
    }

    @Test
    public void get() {
        Restaurant restaurant = service.get(RESTAURANT_1);
        assertThat(restaurant).usingRecursiveComparison()
                .ignoringFields(fieldsToIgnore)
                .isEqualTo(restaurant1);
    }

    @Test
    public void getTopRestaurant() {
        assertThat(service.getTopRestaurant()).usingRecursiveComparison()
                .ignoringFields(fieldsToIgnore)
                .isEqualTo(restaurant3);
    }

    @Test
    public void getAll() {
        assertThat(service.getAll()).usingElementComparatorIgnoringFields(fieldsToIgnore)
                .isEqualTo(restaurants);
    }

    @Test
    public void getWithMenu() {
        Restaurant restaurant = service.getWithMenu(RESTAURANT_1);
        assertThat(restaurant).usingRecursiveComparison()
                .ignoringFields(fieldsToIgnore)
                .isEqualTo(restaurant1);
    }

}