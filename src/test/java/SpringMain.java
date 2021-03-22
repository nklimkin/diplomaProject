import org.springframework.context.support.ClassPathXmlApplicationContext;
import repository.JpaVoteRep;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "classpath:spring/spring-app.xml",
                "classpath:spring/spring-db.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(context.getBeanDefinitionNames()));

            JpaVoteRep jpa = context.getBean(JpaVoteRep.class);

            jpa.getAll().forEach(System.out::println);

        }
    }
}
