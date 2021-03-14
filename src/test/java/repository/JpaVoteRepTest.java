package repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class JpaVoteRepTest {

    @Autowired
    private JpaHistoryOfVoteRep rep;

    @Test
    public void save() {
        rep.save(HistoryOfVoteTestData.getNew(),
                UserTestData.USER1,
                RestaurantTestData.RESTAURANT_1);
        assertThat(rep.get(HistoryOfVoteTestData.VOTE_NEW)).isEqualToIgnoringGivenFields(
                HistoryOfVoteTestData.newVote,"restaurant", "user");
    }

    @Test
    public void delete() {
        rep.delete(HistoryOfVoteTestData.VOTE_1);
        assertThat(rep.get(HistoryOfVoteTestData.VOTE_1)).isEqualTo(null);
    }

    @Test
    public void get() {
        assertThat(rep.get(HistoryOfVoteTestData.VOTE_1)).isEqualToIgnoringGivenFields(HistoryOfVoteTestData.vote1,
                "restaurant", "user");
    }

    @Test
    public void getAll() {
        assertThat(rep.getAll()).usingElementComparatorIgnoringFields("restaurant", "user")
                .containsExactlyInAnyOrder(HistoryOfVoteTestData.vote1,
                        HistoryOfVoteTestData.vote2,
                        HistoryOfVoteTestData.vote3);
    }

    @Test
    public void getHistoryOfVoteByUserId() {
        assertThat(rep.getHistoryOfVoteByUserId(UserTestData.USER2)).isEqualToIgnoringGivenFields(
                HistoryOfVoteTestData.vote2, "restaurant", "user");
    }
}