import org.springframework.context.support.ClassPathXmlApplicationContext;
import repository.JpaUserRep;
import repository.UserRepository;
import repository.UserTestData;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:spring/spring-app.xml",
                "classpath:spring/spring-db.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(context.getBeanDefinitionNames()));

            JpaUserRep jpaUserRep = context.getBean(JpaUserRep.class);

            jpaUserRep.getAll().forEach(System.out::println);

            jpaUserRep.save(UserTestData.updated);

            jpaUserRep.getAll().forEach(System.out::println);
        }
    }
}
