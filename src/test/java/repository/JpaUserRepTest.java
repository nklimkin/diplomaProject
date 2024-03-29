package repository;

import model.Status;
import model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import testData.UserTestData;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles("h2")
public class JpaUserRepTest {

    @Autowired
    private JpaUserRep rep;

    private static final Comparator<User> SORT_BY_NAME = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };

    @Test
    public void save() {
        User save = rep.save(UserTestData.getNew());
        User actual = rep.get(UserTestData.NEW);
        User expected = UserTestData.newUser;
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "password");
    }

    @Test
    public void get() {
        User actual = rep.get(UserTestData.USER1);
        User expected = UserTestData.user1;
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "password");
    }

    @Test
    public void getByEmail() {
        User actual = rep.getByEmail("user1@email.ru");
        User expected = UserTestData.user1;
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "password");
    }

    @Test
    public void getAll() {
        List<User> actualUsers = rep.getAll();
        assertThat(actualUsers).usingElementComparatorIgnoringFields("registered", "password")
                .containsExactlyInAnyOrder(UserTestData.user1, UserTestData.user2, UserTestData.admin1);
    }

    @Test
    public void delete() {
        rep.delete(UserTestData.USER1);
        assertThat(rep.get(UserTestData.USER1).getStatus()).isEqualTo(Status.DELETE);
    }

    @Test
    public void update() {
        rep.save(UserTestData.getUpdated());
        assertThat(rep.get(UserTestData.USER1)).isEqualToIgnoringGivenFields(UserTestData.updated
                , "registered");

    }
}