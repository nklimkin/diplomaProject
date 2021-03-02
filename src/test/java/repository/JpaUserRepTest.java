package repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",

})
@RunWith(SpringRunner.class)
public class JpaUserRepTest {

    @Autowired
    private JpaUserRep rep;

    @Test
    public void save() {
    }

    @Test
    public void get() {
    }

    @Test
    public void getByEmail() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void delete() {
    }
}