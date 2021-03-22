package repository;

import model.Vote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import testData.RestaurantTestData;
import testData.UserTestData;
import testData.VoteTestData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class JpaVoteRepTest {

    @Autowired
    private JpaVoteRep rep;

    @Test
    public void save() {
        rep.save(VoteTestData.getNew(),
                UserTestData.USER1, RestaurantTestData.RESTAURANT_1);
        assertThat(rep.get(VoteTestData.VOTE_NEW)).isEqualToIgnoringGivenFields(
                VoteTestData.newVote,"restaurant", "user");
    }

    @Test
    public void delete() {
        rep.delete(VoteTestData.VOTE_1);
        assertThat(rep.get(VoteTestData.VOTE_1)).isEqualTo(null);
    }

    @Test
    public void get() {
        assertThat(rep.get(VoteTestData.VOTE_1)).isEqualToIgnoringGivenFields(VoteTestData.vote1,
                "restaurant", "user");
    }

    @Test
    public void getAll() {
        assertThat(rep.getAll()).usingElementComparatorIgnoringFields("restaurant", "user")
                .containsExactlyInAnyOrder(VoteTestData.vote1,
                        VoteTestData.vote2);
    }

    @Test
    public void getAllVoteByRestaurant() {
        assertThat(rep.getAllVoteByRestaurant(RestaurantTestData.RESTAURANT_1)).usingElementComparatorIgnoringFields(
                "restaurant", "user").isEqualTo(List.of(VoteTestData.vote1,
                VoteTestData.vote2));
    }
}