package service;

import model.Vote;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import testData.RestaurantTestData;
import testData.UserTestData;
import testData.VoteTestData;
import util.Exception.NotFoundException;
import util.VoteUtil;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static testData.VoteTestData.*;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles("h2")
public class VoteServiceTest {

    @Autowired
    private VoteService voteService;
    @Autowired
    private RestaurantService restaurantService;

    private final String[] fieldToIgnore = new String[]{"localTime", "restaurant", "user"};

    @Test
    public void create() {
        voteService.create(VoteTestData.getNewTo(), UserTestData.USER1, RestaurantTestData.RESTAURANT_1);
        assertThat(voteService.get(VOTE_NEW)).usingRecursiveComparison()
                .ignoringFields(fieldToIgnore)
                .isEqualTo(newVote);

        assertThat((double) Math.round(restaurantService.get(RestaurantTestData.RESTAURANT_1).getRating() * 100) / 100)
                .isEqualTo(3.73);
    }

    @Test
    public void updateWithoutChangingRestaurant() {
        voteService.update(getUpdatedTo(), UserTestData.USER1, RestaurantTestData.RESTAURANT_1);
        assertThat(voteService.get(VOTE_1)).usingRecursiveComparison()
                .ignoringFields(fieldToIgnore)
                .isEqualTo(updatedVoteWithoutChangingRestaurant);

        assertThat((double) Math.round(restaurantService.get(RestaurantTestData.RESTAURANT_1).getRating() * 100) / 100)
                .isEqualTo(1);
    }

    @Test
    public void updateWithChangingRestaurant() {
        voteService.update(getUpdatedTo(), UserTestData.USER1, RestaurantTestData.RESTAURANT_2);
        assertThat(voteService.get(VOTE_1)).usingRecursiveComparison()
                .ignoringFields(fieldToIgnore)
                .isEqualTo(updatedVoteWithChangingRestaurant);

        assertThat((double) Math.round(restaurantService.get(RestaurantTestData.RESTAURANT_1).getRating() * 100) / 100)
                .isEqualTo(0);

        assertThat((double) Math.round(restaurantService.get(RestaurantTestData.RESTAURANT_2).getRating() * 100) / 100)
                .isEqualTo(3);
    }

    @Test
    public void delete() {
        voteService.delete(VOTE_1);
        assertThrows(NotFoundException.class, () -> voteService.get(VOTE_1));

        assertThat((double) Math.round(restaurantService.get(RestaurantTestData.RESTAURANT_1).getRating() * 100) / 100)
                .isEqualTo(0);
    }

    @Test
    public void getAll() {
        assertThat(voteService.getAll()).usingElementComparatorIgnoringFields(fieldToIgnore).containsExactlyInAnyOrder(vote1, vote2);

    }

    @Test
    public void get() {
        assertThat(voteService.get(VOTE_1)).usingRecursiveComparison()
                .ignoringFields(fieldToIgnore)
                .isEqualTo(vote1);
    }

    @Test
    public void getAllVoteByRestaurant() {
        assertThat(voteService.getAllVoteByRestaurant(RestaurantTestData.RESTAURANT_1))
                .usingElementComparatorIgnoringFields(fieldToIgnore)
                .containsExactlyInAnyOrder(vote1);
    }

    @Test
    public void getVotesWithUserAndRestaurant() {
        List<Vote> votes = voteService.getVotesWithUserAndRestaurant();
        votes.forEach(vote -> System.out.println(vote + " " + vote.getUser() + " " + vote.getRestaurant()));
        assertThat(votes)
                .usingElementComparatorIgnoringFields(fieldToIgnore)
                .containsExactlyInAnyOrder(vote1, vote2);

    }

}